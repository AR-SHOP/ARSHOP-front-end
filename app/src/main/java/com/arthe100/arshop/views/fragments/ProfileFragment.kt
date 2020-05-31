package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.AuthUiAction
import com.arthe100.arshop.scripts.mvi.Auth.AuthViewModel
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.Profile.ProfileState
import com.arthe100.arshop.scripts.mvi.Profile.ProfileUiAction
import com.arthe100.arshop.scripts.mvi.Profile.ProfileViewModel
import com.arthe100.arshop.scripts.mvi.cart.CartViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.profile_fragment_layout.*
import javax.inject.Inject


class ProfileFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val session: UserSession
): BaseFragment() {


    private lateinit var messageManager: MessageManager
    private lateinit var model: ProfileViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var autViewModel: AuthViewModel
    private lateinit var dialogBox: DialogBoxManager
    private val TAG = ProfileFragment::class.simpleName



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        messageManager = MessageManager()
        dialogBox = DialogBoxManager()
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(ProfileViewModel::class.java)
        cartViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(CartViewModel::class.java)
        autViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(AuthViewModel::class.java)
        return inflater.inflate(R.layout.profile_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentViewState.observe(viewLifecycleOwner , Observer(::render))
    }

    override fun onStart() {
        super.onStart()

        user_account_button?.setOnClickListener {
            loadFragment(ProfileInfoFragment::class.java)
        }

        wishlist_button?.setOnClickListener {
            loadFragment(WishListFragment::class.java)
        }

        logout_button?.setOnClickListener{
            session.logout()
            autViewModel.onEvent(AuthUiAction.LogoutAction)
            model.onEvent(ProfileUiAction.LogoutAction)
            cartViewModel.logout()
        }   
        model.onEvent(ProfileUiAction.GetHomePageProfileAction)
    }

    override fun toString(): String {
        return "Profile"
    }

    private fun render(state: ProfileState){
        when(state) {
            is ProfileState.Idle -> {
                dialogBox.cancel()
//                requireView().visibility = View.VISIBLE
            }

            is ProfileState.GetProfileFailure -> {
//                requireView().visibility = View.VISIBLE
                dialogBox.showDialog(requireContext(), MessageType.ERROR, "خطا در برقراری ارتباط با سرور")
                messageManager.toast(requireContext(), state.throwable.toString())
            }

            is ProfileState.GetProfileSuccess -> {
                dialogBox.cancel()
//                requireView().visibility = View.VISIBLE
                val user = state.userInfo
                model.currentProfile = user
                name.text = if(user.fName.isEmpty())
                    "نام و نام‌خانوادگی"
                else
                    "${state.userInfo.fName} ${state.userInfo.lName}"

                email.text = if(user.email.isEmpty())
                    "ItsMe@Gmail.com"
                else
                    state.userInfo.email
                phone_number.text = if(user.phone.isNullOrEmpty())
                    "09112223344"
                else
                    state.userInfo.phone
            }

            is ProfileState.LoadingState -> {
//                requireView().visibility = View.INVISIBLE
                dialogBox.showDialog(requireActivity(), MessageType.LOAD)
            }
            is ProfileState.LogoutState -> {
                loadFragment(LoginFragment::class.java)
            }
        }
    }
}
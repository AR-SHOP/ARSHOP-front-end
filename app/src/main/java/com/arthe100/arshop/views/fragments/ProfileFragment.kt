package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.Profile.ProfileState
import com.arthe100.arshop.scripts.mvi.Profile.ProfileUiAction
import com.arthe100.arshop.scripts.mvi.Profile.ProfileViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.profile_fragment_layout.*
import javax.inject.Inject

class ProfileFragment : BaseFragment() {
    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var session: UserSession

    private lateinit var messageManager: MessageManager
    private lateinit var model: ProfileViewModel
    private lateinit var dialogBox: DialogBoxManager
    private val TAG = ProfileFragment::class.simpleName

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        messageManager = MessageManager()
        dialogBox = DialogBoxManager()
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(ProfileViewModel::class.java)
        return inflater.inflate(R.layout.profile_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentViewState.observe(viewLifecycleOwner , Observer(::render))
    }

    override fun onStart() {
        super.onStart()
        model.onEvent(ProfileUiAction.GetHomePageProfileAction)
    }

    override fun toString(): String {
        return "Profile"
    }

    private fun render(state: ProfileState){
        when(state) {
            is ProfileState.Idle -> {
                dialogBox.cancel()
                requireView().visibility = View.VISIBLE
            }

            is ProfileState.GetProfileFailure -> {
                requireView().visibility = View.VISIBLE
                dialogBox.showDialog(requireContext(), MessageType.ERROR, "خطا در برقراری ارتباط با سرور")
                messageManager.toast(requireContext(), state.throwable.toString())
            }

            is ProfileState.GetProfileSuccess -> {
                dialogBox.cancel()
                requireView().visibility = View.VISIBLE
                val user = state.userInfo

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
                requireView().visibility = View.INVISIBLE
                dialogBox.showDialog(requireActivity(), MessageType.LOAD)
            }
        }
    }
}
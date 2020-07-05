package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Profile.ProfileViewModel
import com.arthe100.arshop.scripts.mvi.base.ProfileState
import com.arthe100.arshop.scripts.mvi.base.ProfileUiAction
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import kotlinx.android.synthetic.main.edit_profile_info_fragment_layout.*
import kotlinx.android.synthetic.main.profile_info_fragment_layout.*
import javax.inject.Inject

class EditProfileInfoFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory
): BaseFragment(){

    private lateinit var model : ProfileViewModel
    private lateinit var messageManager: MessageManager
    private lateinit var dialogBox: DialogBoxManager
    private val TAG = EditProfileInfoFragment::class.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        messageManager = MessageManager()
        dialogBox = DialogBoxManager()
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(ProfileViewModel::class.java)
        return inflater.inflate(R.layout.edit_profile_info_fragment_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(edit_profile_toolbar)
        model.currentViewState.observe(viewLifecycleOwner , Observer(::render))
    }

    override fun onStart() {
        super.onStart()

        model.onEvent(ProfileUiAction.GetHomePageProfileAction)

        save_changes.setOnClickListener {
            model.onEvent(ProfileUiAction.EditProfileInfoAction)
        }

        cancel_changes.setOnClickListener {
            loadFragment(ProfileInfoFragment::class.java)
        }
    }

    override fun render(state: ViewState) {
        when(state) {
            is ViewState.IdleState -> {
                dialogBox.cancel()
//                requireView().visibility = View.VISIBLE
            }

            is ViewState.Failure -> {
//                requireView().visibility = View.VISIBLE
                dialogBox.showDialog(requireContext(), MessageType.ERROR, state.throwable.toString())
                messageManager.toast(requireContext(), state.throwable.toString())
            }

            is ProfileState.GetProfileSuccess -> {
                dialogBox.cancel()
//                requireView().visibility = View.VISIBLE
                val user = state.userInfo
                model.currentProfile = user

                name_last_name_editText.setText(user.fName + " " + user.lName)

                phone_number_editText.setText(user.phone)

                email_editText.setText(user.email)

                NID_editText.setText(user.ssId)
            }

            is ProfileState.EditProfileSuccess -> {
                dialogBox.showDialog(requireContext() , MessageType.SUCCESS)

                val user = state.editInfo

                user.fName = name_last_name_editText.text.toString()

                user.phone = phone_number_editText.text.toString()

                user.email = email_editText.text.toString()

                user.ssId = NID_editText.text.toString()

                model.currentProfile = user
            }

            is ViewState.LoadingState -> {
//                requireView().visibility = View.INVISIBLE
                dialogBox.showDialog(requireActivity(), MessageType.LOAD)
            }
            is ProfileState.LogoutState -> {
                loadFragment(LoginFragment::class.java)
            }
        }
    }

    override fun toString(): String {
        return "EditProfileInfoFragment"
    }
}
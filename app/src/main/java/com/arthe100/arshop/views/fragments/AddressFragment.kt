package com.arthe100.arshop.views.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.arthe100.arshop.R
import com.arthe100.arshop.models.Address
import com.arthe100.arshop.models.Comment
import com.arthe100.arshop.scripts.mvi.Profile.ProfileViewModel
import com.arthe100.arshop.scripts.mvi.base.ProfileState
import com.arthe100.arshop.scripts.mvi.base.ProfileUiAction
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.base.*
import com.arthe100.arshop.views.dialogBox.AddressDialog
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import com.arthe100.arshop.views.interfaces.ILoadFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.dialog_address_layout.*
import kotlinx.android.synthetic.main.dialog_comment_layout.*
import kotlinx.android.synthetic.main.dialog_comment_layout.close_btn
import kotlinx.android.synthetic.main.fragment_address.*
import javax.inject.Inject

class AddressFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val dialogBox: DialogBoxManager
) : BaseFragment(), ILoadFragment {

    private lateinit var address: String
    private lateinit var addressDialog: AddressDialog
    private lateinit var addressAdapter: GenericAdapter<Address>
    private lateinit var model: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar?.visibility = View.INVISIBLE
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(ProfileViewModel::class.java)
        model.currentViewState.observe(viewLifecycleOwner , Observer(::render))
        addressDialog = setAddressDialog()
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    override fun render(state: ViewState) {
        when(state){
            is ViewState.IdleState -> dialogBox.cancel()
            is ViewState.Failure -> dialogBox.showDialog(requireContext(), MessageType.ERROR, state.throwable.toString())
            is ViewState.LoadingState -> dialogBox.showDialog(requireActivity(), MessageType.LOAD)
            is ProfileState.DeleteAddressSuccess -> {
                no_address_image?.visibility = if(addressAdapter.itemCount == 0) View.VISIBLE else View.INVISIBLE
                no_address_text?.visibility = if(addressAdapter.itemCount == 0) View.VISIBLE else View.INVISIBLE
                dialogBox.cancel()
            }
            is ProfileState.GetAddressesSuccess -> {
                dialogBox.cancel()
                no_address_image?.visibility = if(state.addresses.isEmpty()) View.VISIBLE else View.INVISIBLE
                no_address_text?.visibility = if(state.addresses.isEmpty()) View.VISIBLE else View.INVISIBLE
                addressAdapter.addItems(state.addresses)}
            is ProfileState.GetAddressSuccess -> {
                dialogBox.cancel()
            }
            is ProfileState.UpdateAddressSuccess -> {
                addressAdapter.changeItem(state.address,addressAdapter.getItemAt(model.currentAddress!!))
                addressDialog.close()
                dialogBox.cancel()
            }
            is ProfileState.CreateAddressSuccess -> {
                dialogBox.cancel()
                addressDialog.close()
                addressAdapter.addItem(state.address)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setRecyclerView()

        model.onEvent(ProfileUiAction.GetAddressListAction)
        create_address_btn?.setOnClickListener {
            addressDialog.open()
        }

//        edit_address_btn?.setOnClickListener {
//            if(model.currentAddress == null) return@setOnClickListener
//            addressDialog.openInEditMode(model.currentAddress!!)
//        }
    }

    private fun setAdapter() {
        addressAdapter = object : GenericAdapter<Address>() {
            override fun getLayoutId(position: Int, obj: Address): Int = R.layout.item_address
        }

        addressAdapter.apply {
            setDiffUtil(object : GenericItemDiff<Address> {
                override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
                    return oldItem.addressLine == newItem.addressLine
                }
            })
            setItemListener(object: OnItemClickListener<Address> {
                override fun onClickItem(data: Address) {
                    model.currentAddress = data
                }

            })

            setViewListeners(listOf(
                ViewListeners(
                    R.id.delete_btn , object: OnItemClickListenerForView<Address> {
                        override fun onClickItem(data: Address, position: Int) {
                            model.onEvent(ProfileUiAction.DeleteAddressAction(data.id))
                            removeItem(position)
                        }
                    }),
                ViewListeners(
                    R.id.edit_btn, object : OnItemClickListenerForView<Address> {
                        override fun onClickItem(data: Address, position: Int) {
                            addressDialog.openInEditMode(data)
                        }
                    })
            ))
        }
    }

    private fun setRecyclerView() {
        if (!this::addressAdapter.isInitialized) setAdapter()
        address_recycler_view?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = addressAdapter
        }

    }


    private fun setAddressDialog() : AddressDialog {
        return AddressDialog(requireContext() , model)
    }

//    private fun showAddressDialog() {
//        if (this::addressDialog.isInitialized && addressDialog.isShowing) {
//            addressDialog.dismiss()
//            addressDialog = setAddressDialog()
//            addressDialog.show()
//        } else {
//            addressDialog = setAddressDialog()
//            addressDialog.show()
//        }
//
//        var province = addressDialog.province.text.toString()
//        var city = addressDialog.city.text.toString()
//        var homeAddress = addressDialog.home_details.text.toString()
//
//        if (!province.isNullOrEmpty() && !city.isNullOrEmpty() && !homeAddress.isNullOrEmpty())
//        {
//            address = "$province،$city،$homeAddress"
//        }
//    }

}

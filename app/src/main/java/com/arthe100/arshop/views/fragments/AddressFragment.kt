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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.arthe100.arshop.R
import com.arthe100.arshop.models.Comment
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.base.GenericAdapter
import com.arthe100.arshop.views.adapters.base.GenericDiffUtil
import com.arthe100.arshop.views.adapters.base.GenericItemDiff
import com.arthe100.arshop.views.interfaces.ILoadFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.dialog_address_layout.*
import kotlinx.android.synthetic.main.dialog_comment_layout.*
import kotlinx.android.synthetic.main.dialog_comment_layout.close_btn
import kotlinx.android.synthetic.main.fragment_address.*
import javax.inject.Inject

class AddressFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory
) : BaseFragment(), ILoadFragment {

    private lateinit var address: String
    private lateinit var addressDialog: Dialog
    private lateinit var addressAdapter: GenericAdapter<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar?.visibility = View.INVISIBLE
        setAddressDialog()
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    override fun onStart() {
        super.onStart()
        setRecyclerView()


        add_new_address_btn?.setOnClickListener {
            showAddressDialog()
        }
    }

    private fun setAdapter() {
        addressAdapter = object : GenericAdapter<String>() {
            override fun getLayoutId(position: Int, obj: String): Int = R.layout.item_address
        }

        addressAdapter.apply {
            setDiffUtil(object : GenericItemDiff<String> {
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }
            })
        }
    }

    private fun setRecyclerView() {
        if (!this::addressDialog.isInitialized) setAdapter()
        address_recycler_view?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = addressAdapter
        }

    }


    private fun setAddressDialog() : Dialog {

        var resultDialog = Dialog(requireContext())
        resultDialog.setContentView(R.layout.dialog_address_layout)
        resultDialog.close_btn?.setOnClickListener {
            resultDialog.cancel()
        }

        resultDialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        resultDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return resultDialog
    }

    private fun showAddressDialog() {
        if (this::addressDialog.isInitialized && addressDialog.isShowing) {
            addressDialog.dismiss()
            addressDialog = setAddressDialog()
            addressDialog.show()
        } else {
            addressDialog = setAddressDialog()
            addressDialog.show()
        }

        var province = addressDialog.province.text.toString()
        var city = addressDialog.city.text.toString()
        var homeAddress = addressDialog.home_details.text.toString()

        if (!province.isNullOrEmpty() && !city.isNullOrEmpty() && !homeAddress.isNullOrEmpty())
        {
            address = "$province،$city،$homeAddress"
        }
    }

}

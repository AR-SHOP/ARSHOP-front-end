package com.arthe100.arshop.views.dialogBox

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Address
import com.arthe100.arshop.scripts.mvi.Profile.ProfileViewModel
import com.arthe100.arshop.scripts.mvi.base.ProfileUiAction
import kotlinx.android.synthetic.main.dialog_address_layout.*

class AddressDialog(
    private val kontext: Context,
    private val model: ProfileViewModel
) : Dialog(kontext) {

    private val viewId = R.layout.dialog_address_layout
    private var mode = Mode.Normal
    private var address: Address? = null
    init {
        setContentView(viewId)
        close_btn?.setOnClickListener{ close() }
        add_new_address_btn?.setOnClickListener {
            when (mode){
                Mode.Normal -> addNewAddress()
                Mode.Edit ->updateAddress()
            }
        }

        window!!.attributes.windowAnimations = R.style.DialogAnimation
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun close(){
        this.cancel()
        first_name?.setText("")
        last_name?.setText("")
        national_code?.setText("")
        reciever_phone_number?.setText("")
        postal_code?.setText("")
        city?.setText("")
        province?.setText("")
        home_details?.setText("")
        plaque?.setText("")
        floor?.setText("")
        self_reciever?.isChecked = false
    }

    fun open(){
        mode = Mode.Normal
        show()
    }
    fun openInEditMode(address: Address){
        mode = Mode.Edit
        show()
        first_name?.setText(address.firstName)
        last_name?.setText(address.lastName)
        national_code?.setText(address.nationalId)
        reciever_phone_number?.setText(address.phone)
        postal_code?.setText(address.postalCode)
        city?.setText(address.city)
        province?.setText(address.province)
        home_details?.setText(address.addressLine)
        plaque?.setText(address.plaque.toString())
        floor?.setText(address.floorNumber.toString())
        self_reciever?.isChecked = isReceiver(address)
    }

    private fun addNewAddress(){
        val address = getAddress() ?: return
        model.onEvent(ProfileUiAction.CreateAddressAction(address))
    }

    private fun updateAddress(){
        val address = updateAddress(model.currentAddress!!) ?: return
        model.onEvent(ProfileUiAction.UpdateAddressAction(address))
    }

    private fun isReceiver(address: Address) : Boolean{
        val prof = model.currentProfile ?: throw NullPointerException("profile is null")
        return prof.fName == address.firstName &&
                prof.lName == address.lastName &&
                prof.phone == address.phone &&
                prof.ssId == address.nationalId
    }

    private fun getAddress() : Address?{
        try {
            val province = province?.text.toString()
            val city = city?.text.toString()
            val plaque = plaque?.text.toString().toInt()
            val floor = floor?.text.toString().toInt()
            val postalCode = postal_code?.text.toString()
            val homeAddress = home_details?.text.toString()
            val isReceiver = self_reciever?.isChecked!!
            val fName = first_name?.text.toString()
            val lName = last_name?.text.toString()
            val nationalId = national_code?.text.toString()
            val phone = reciever_phone_number?.text.toString()
            val prof = model.currentProfile ?: throw NullPointerException("profile is null")
            return Address(
                id = -1,
                firstName = if(isReceiver) prof.fName else fName,
                lastName = if(isReceiver) prof.lName else lName,
                nationalId = if(isReceiver) prof.ssId else nationalId,
                phone = if(isReceiver) prof.phone else phone,
                postalCode = postalCode,
                city = city,
                province = province,
                country = "IR",
                addressLine = homeAddress,
                plaque = plaque,
                floorNumber = floor,
                user = prof.id
            )
        }catch (t: Throwable){
            return null
        }
    }


    private fun updateAddress(address: Address) : Address?{
        try {
            val province = province?.text.toString()
            val city = city?.text.toString()
            val plaque = plaque?.text.toString().toInt()
            val floor = floor?.text.toString().toInt()
            val postalCode = postal_code?.text.toString()
            val homeAddress = home_details?.text.toString()
            val isReceiver = self_reciever?.isChecked!!
            val fName = first_name?.text.toString()
            val lName = last_name?.text.toString()
            val nationalId = national_code?.text.toString()
            val phone = reciever_phone_number?.text.toString()
            val prof = model.currentProfile ?: throw NullPointerException("profile is null")
            return Address(
                id = address.id,
                firstName = if(isReceiver) prof.fName else fName,
                lastName = if(isReceiver) prof.lName else lName,
                nationalId = if(isReceiver) prof.ssId else nationalId,
                phone = if(isReceiver) prof.phone else phone,
                postalCode = postalCode,
                city = city,
                province = province,
                country = "IR",
                addressLine = homeAddress,
                plaque = plaque,
                floorNumber = floor,
                user = address.user
            )
        }catch (t: Throwable){
            return null
        }
    }

    enum class Mode{
        Normal,
        Edit
    }

}
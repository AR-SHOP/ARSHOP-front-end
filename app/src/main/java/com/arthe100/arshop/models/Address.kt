package com.arthe100.arshop.models

data class Address(
    var id: Long,
    val firstName: String,
    val lastName: String,
    val nationalId: String,
    val phone: String,
    val postalCode: String,
    val city: String,
    val province: String,
    val country: String,
    val addressLine: String,
    val plaque: Int,
    val floorNumber: Int,
    val user: Long
){
//    override fun toString(): String {
//        return "name: $firstName $lastName \n" +
//                "id: $nationalId\n" +
//                "phone: $phone\n" +
//                "address: $province $city $addressLine " +
//                "floor num: $floorNumber" +
//                "plaque: $plaque"
//    }
}
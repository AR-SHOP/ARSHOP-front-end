package com.arthe100.arshop.models

sealed class Product
{
    data class ArProduct(
        val name : String
        , val description : String
        , val manufacturer : String
        , val price : Long
        , val image : String
    ): Product(){}

    data class NormalProduct(
        val name : String
        , val description : String
        , val manufacturer : String
        , val price : Long
    ): Product(){}
}
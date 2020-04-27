package com.arthe100.arshop.views.data

class DataSource {
    companion object{

        var product1: Product.ProductBuilder = Product.ProductBuilder()
            .setTitle("Congratulations!")
            .setPrice(998989)
            .setImageUrl("https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png")

        var product2 = Product.ProductBuilder()
            .setTitle("Time to Build a Kotlin App!")
            .setPrice(9898989)
            .setImageUrl("https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/time_to_build_a_kotlin_app.png")

        fun createDataSet(): ArrayList<Product>{
            val list = ArrayList<Product>()
            list.add(
                product1.build()
            )
            list.add(
                product2.build()
            )

            list.add(Product.ProductBuilder()
                .setTitle("Interviewing a Web Developer and YouTuber")
                .setPrice(2323432)
                .setImageUrl("https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/coding_for_entrepreneurs.png")
                .build()
            )
            list.add(
                Product.ProductBuilder()
                    .setTitle("Freelance Android Developer (Vasiliy Zukanov)")
                    .setPrice(82345)
                    .setImageUrl("https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/freelance_android_dev_vasiliy_zukanov.png")
                    .build()
            )
            return list
        }
    }

}
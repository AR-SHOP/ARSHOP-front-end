package com.arthe100.arshop.views

class Data private constructor(builder: DataBuilder) {
    var title: String? = null
    var description: String? = null
    var imageUrl: String? = null
    var price: Int = 0

    init {
        this.title = builder.title
        this.description = builder.description
        this.imageUrl = builder.imageUrl
        this.price = builder.price
    }

    class DataBuilder {
        var title: String? = null
            private set
        var description: String? = null
            private set
        var imageUrl: String? = null
            private set
        var price: Int = 0
            private set

        fun setTitle(title: String) = apply { this.title = title }
        fun setDescription(description: String) = apply { this.description = description }
        fun setImageUrl(imageUrl: String) = apply { this.imageUrl = imageUrl }
        fun setPrice(price: Int) = apply { this.price = price }
        public fun build() = Data(this)
    }
}
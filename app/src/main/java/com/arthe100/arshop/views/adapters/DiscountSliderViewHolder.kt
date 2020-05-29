package com.arthe100.arshop.views.adapters

import android.view.View
import com.arthe100.arshop.R
import com.arthe100.arshop.models.HomeSales
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.discount_card_view.view.*

class DiscountSliderViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {


    private var discountImage = itemView.discount_card_image

    fun bind(data: HomeSales) {

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.white_background)
            .error(R.drawable.white_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(data.image)
            .into(discountImage)
    }
}
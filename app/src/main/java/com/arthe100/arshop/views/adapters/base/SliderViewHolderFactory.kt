package com.arthe100.arshop.views.adapters.base

import android.view.View
import com.arthe100.arshop.R
import com.arthe100.arshop.models.HomeSales
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.discount_card_view.view.*

object SliderViewHolderFactory {
    fun create(view: View, viewType: Int): SliderViewAdapter.ViewHolder {
        return when (viewType) {
            R.layout.discount_card_view -> DiscountSliderViewHolder(view)
            else -> throw IllegalArgumentException("Wrong view type")
        }
    }
}

class DiscountSliderViewHolder(itemView: View)
    : SliderViewAdapter.ViewHolder(itemView), GenericSliderAdapter.Binder<HomeSales> {

    private var discountImage = itemView.discount_card_image

    override fun bind(data: HomeSales, clickListener: OnItemClickListener<HomeSales>?) {

        itemView.apply {
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(data.image)
                .into(discountImage)

            setOnClickListener{ clickListener?.onClickItem(data) }
        }
    }
}
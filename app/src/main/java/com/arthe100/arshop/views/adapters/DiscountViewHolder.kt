package com.arthe100.arshop.views.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.HomeSales
import com.arthe100.arshop.views.adapters.BaseItemViewHolder
import com.arthe100.arshop.views.adapters.OnItemClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.discount_card_view.view.*

class DiscountViewHolder(itemView: View)
    : BaseItemViewHolder<HomeSales>(itemView) {

    var discountImageUrl = itemView.discount_card_image


    override fun bind(data: HomeSales, listener: OnItemClickListener) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_empty_background)
            .error(R.drawable.ic_empty_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(data.image)
            .into(discountImageUrl)


        itemView.setOnClickListener{
            var position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }
}
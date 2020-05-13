package com.arthe100.arshop.views.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.discount_card_view.view.*

class DiscountViewHolder(itemView: View)
    : BaseItemViewHolder<String>(itemView) {

    var discountImageUrl = itemView.discount_card_image


    override fun bind(data: String, listener: OnItemClickListener) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_empty_background)
            .error(R.drawable.ic_empty_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(data)
            .into(discountImageUrl)


        itemView.setOnClickListener{
            var position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }
}
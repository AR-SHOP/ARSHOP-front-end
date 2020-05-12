package com.arthe100.arshop.views.Adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.cart_item.view.*

class CartItemViewHolder(itemView: View) : BaseItemViewHolder<Product>(itemView) {


    var dataName = itemView.cart_item_name
    var dataPrice = itemView.cart_item_price
    var dataImage = itemView.cart_item_image

    override fun bind(data: Product, listener: OnItemClickListener) {
        dataName.text = data.name
        dataPrice.text = data.price.toString()

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(data.thumbnail)
            .into(dataImage)

        itemView.setOnClickListener{
            var position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
}
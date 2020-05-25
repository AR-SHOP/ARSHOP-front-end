package com.arthe100.arshop.views.adapters.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.product_fragment_layout.view.*
import kotlinx.android.synthetic.main.product_grid_item.view.*

object ViewHolderFactory {
    fun create(view: View, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.product_grid_item -> HomePageProductViewHolder(view)
            else -> throw IllegalArgumentException("Wrong view type")
        }
    }
}

class HomePageProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , GenericAdapter.Binder<Product> {
    override fun bind(data: Product, clickListener: OnItemClickListener<Product>?) {
        itemView.apply {
            itemView.product_name.text = data.name
            itemView.product_price.text = data.price.toString()
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background)

            Glide.with(context)
                .applyDefaultRequestOptions(requestOptions)
                .load(data.thumbnail)
                .into(itemView.product_image)

            setOnClickListener{ clickListener?.onClickItem(data) }
        }
    }
}

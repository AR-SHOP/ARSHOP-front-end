package com.arthe100.arshop.views.adapters.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.CartItem
import com.arthe100.arshop.models.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.cart_item.view.*
import kotlinx.android.synthetic.main.product_fragment_layout.view.cart_count_text
import kotlinx.android.synthetic.main.product_grid_item.view.*

object ViewHolderFactory {
    fun create(view: View, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.product_grid_item -> HomePageProductViewHolder(view)
            R.layout.cart_item -> CartItemViewHolder(view)
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

class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , GenericAdapter.BinderMultiple<CartItem>{

    override fun bind(
        data: CartItem,
        itemListener: OnItemClickListener<CartItem>?,
        viewListeners: List<ViewListeners<CartItem>>?
    ) {
        itemView.apply {

            val product = data.product

            itemView.cart_item_name.text = product.name
            itemView.cart_item_price.text = product.price.toString()
            itemView.cart_count_text.text = data.quantity.toString()
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(product.thumbnail)
                .into(itemView.cart_item_image)

            viewListeners?.forEach {
                val view = itemView.findViewById(it.id) as View
                view.setOnClickListener { _ ->
                    it.listener?.onClickItem(data , adapterPosition)
                }
            }

            setOnClickListener { itemListener?.onClickItem(data) }
        }
    }


}

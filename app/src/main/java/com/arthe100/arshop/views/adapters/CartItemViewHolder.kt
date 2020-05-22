package com.arthe100.arshop.views.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.CartItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.cart_item.view.*

class CartItemViewHolder(itemView: View) : BaseItemViewHolder<CartItem>(itemView) {


    var dataName = itemView.cart_item_name!!
    var dataPrice = itemView.cart_item_price!!
    var dataImage = itemView.cart_item_image!!
    private val btnPlus = itemView.plus_btn!!
    private val btnMinus = itemView.minus_btn!!
    private val btnDelete = itemView.delete_btn!!
    private val txtQuantity = itemView.cart_count_text!!


    override fun bind(data: CartItem, listener: OnItemClickListener) {
        val product = data.product

        dataName.text = product.name
        dataPrice.text = product.price.toString()
        txtQuantity.text = data.quantity.toString()
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.white_background)
            .error(R.drawable.white_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(product.thumbnail)
            .into(dataImage)

        itemView.setOnClickListener{
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun bind(data: CartItem, listeners: Map<String, OnItemClickListener>) {


        listeners["main"]?.let { bind(data , it) }

        btnPlus.setOnClickListener{
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val newQuantity = (txtQuantity.text.toString().toInt() + 1).coerceIn(0..Int.MAX_VALUE)
                data.quantity = newQuantity
                txtQuantity.text = newQuantity.toString()
                listeners["add"]?.onItemClick(position)
            }
        }
        btnMinus.setOnClickListener{
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val newQuantity = (txtQuantity.text.toString().toInt() - 1).coerceIn(0..Int.MAX_VALUE)
                data.quantity = newQuantity
                txtQuantity.text = newQuantity.toString()
                listeners["minus"]?.onItemClick(position)
            }
        }
        btnDelete.setOnClickListener{
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                data.quantity = 0
                listeners["delete"]?.onItemClick(position)
            }
        }

    }
}

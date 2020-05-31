package com.arthe100.arshop.views.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.views.adapters.base.OnItemClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.product_grid_item.view.*

//class ProductViewHolder(itemView: View) : BaseItemViewHolder<Product>(itemView) {
//
//    private var productImage = itemView.product_image
//    private var productName = itemView.product_name
//    private var productPrice = itemView.product_price
//
//    override fun bind(data: Product, listener: OnItemClickListener<Product>) {
//
//        productName.text = data.name
//        productPrice.text = data.price.toString()
//
//        val requestOptions = RequestOptions()
//            .placeholder(R.drawable.white_background)
//            .error(R.drawable.white_background)
//
//        Glide.with(itemView.context)
//            .applyDefaultRequestOptions(requestOptions)
//            .load(data.thumbnail)
//            .into(productImage)
//
//        itemView.setOnClickListener{
//            val position: Int = adapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                listener.onClickItem(position)
//            }
//        }
//
//    }
//
//    override fun bind(data: Product, listeners: Map<String, OnItemClickListener<Product>>) {
//        super.bind(data, listeners)
//    }
//}
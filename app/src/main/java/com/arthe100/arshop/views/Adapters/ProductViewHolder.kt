package com.arthe100.arshop.views.Adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.product_card_view.view.*
internal class ProductViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView) {
//    private var dataTitle: TextView? = itemView.product_title
//    private var dataImageUrl = itemView.product_image
//    private var dataPrice: TextView? = itemView.product_price
//
    fun bind(data: Product, listener: OnItemClickListener) {

//        dataTitle?.text = data.description
//        dataPrice?.text = data.price.toString()
//
//
//        val requestOptions = RequestOptions()
//            .placeholder(R.drawable.ic_launcher_background)
//            .error(R.drawable.ic_launcher_background)
//
//        Glide.with(itemView.context)
//            .applyDefaultRequestOptions(requestOptions)
//            .load(data.thumbnail)
//            .into(dataImageUrl)
//
//        itemView.setOnClickListener{
//            var position: Int = adapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                listener.onItemClick(position)
//            }
//        }
    }



}
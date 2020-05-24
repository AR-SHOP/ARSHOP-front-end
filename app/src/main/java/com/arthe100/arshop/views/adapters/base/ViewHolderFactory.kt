package com.arthe100.arshop.views.adapters.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product

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

            setOnClickListener{ clickListener?.onClickItem(data) }
        }
    }

}

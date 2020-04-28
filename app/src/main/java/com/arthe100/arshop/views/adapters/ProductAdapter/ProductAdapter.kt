package com.arthe100.arshop.views.adapters.ProductAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product

class ProductAdapter()
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var dataList: List<Product>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.product_card_view, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ProductViewHolder -> {
                holder.bind(dataList[position])
            }
        }
    }
    override fun getItemCount(): Int = dataList.size

    fun submitList(blogList: List<Product>){
        dataList = blogList
    }

}
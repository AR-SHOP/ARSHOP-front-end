package com.arthe100.arshop.views.adapters

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.models.Category
import com.arthe100.arshop.models.Product
import kotlinx.android.synthetic.main.horizontal_product_recycler_view.view.*

class GroupRecyclerViewHolder(itemView: View) : BaseItemViewHolder<Category>(itemView) {

    lateinit var context: Context
    lateinit var productList: ArrayList<Product>

    private var categoryName = itemView.recycler_view_category_name
    private var productsRecyclerView = itemView.products_recycler_view


    override fun bind(data: Category, listener: OnItemClickListener) {
        categoryName.text = data.title
        var productAdapter: ProductAdapter = ProductAdapter()
        productAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                TODO("Not yet implemented")
            }
        })
        productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = productAdapter
        }
        productAdapter.submitList(productList)

        itemView.setOnClickListener{
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    override fun bind(data: Category, listeners: Map<String, OnItemClickListener>) {
        super.bind(data, listeners)
    }
}
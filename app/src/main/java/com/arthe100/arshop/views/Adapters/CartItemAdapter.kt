package com.arthe100.arshop.views.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product

class CartItemAdapter : BaseItemAdapter<Product>() {

    private lateinit var mListener: OnItemClickListener
    lateinit var dataList: List<Product>
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CartItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is CartItemViewHolder -> {
                holder.bind(dataList[position], mListener)
            }
        }

    }

    override fun getItemCount(): Int = dataList.size

    override fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun submitList(data: List<Product>) {
        dataList = data
    }
}
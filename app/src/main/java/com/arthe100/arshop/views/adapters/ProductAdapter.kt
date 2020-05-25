package com.arthe100.arshop.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.views.interfaces.OnItemClickListener

class ProductAdapter : BaseItemAdapter<Product>() {

    private var dataList: ArrayList<Product> = arrayListOf()
    private lateinit var mListener: OnItemClickListener<Product>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_grid_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> {
                holder.bind(dataList[position], mListener)
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun setOnItemClickListener(listener: OnItemClickListener<Product>) {
        mListener = listener
    }

    override fun submitList(data: MutableList<Product>) {
        val result = DiffUtil.calculateDiff(object: DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return dataList[oldItemPosition].id ==
                        data[newItemPosition].id
            }

            override fun getOldListSize(): Int {
                return dataList.size
            }

            override fun getNewListSize(): Int {
                return data.size
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val old = dataList[oldItemPosition]
                val new = data[newItemPosition]
                return old == new
            }

        })
        dataList.clear()
        dataList.addAll(data)
        result.dispatchUpdatesTo(this)
        notifyDataSetChanged()

    }
}
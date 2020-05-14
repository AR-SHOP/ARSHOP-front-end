package com.arthe100.arshop.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Category

class CategoryItemAdapter : BaseItemAdapter<Category>() {

    private lateinit var mListener: OnItemClickListener
    private var dataList: MutableList<Category> = arrayListOf()
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_card_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is CategoryItemViewHolder -> {
                holder.bind(dataList[position], mListener)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun submitList(data: MutableList<Category>) {
        dataList = data
    }
}
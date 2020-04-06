package com.arthe100.arshop.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.views.Data

class DataAdapter(private val dataList: ArrayList<Data>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DataViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.data_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is DataViewHolder -> {
                holder.bind(dataList[position])
            }
        }
    }
    override fun getItemCount(): Int = dataList.size
}
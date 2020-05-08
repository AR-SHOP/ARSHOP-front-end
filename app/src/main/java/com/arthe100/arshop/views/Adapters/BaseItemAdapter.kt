package com.arthe100.arshop.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.views.Adapters.OnItemClickListener

abstract class BaseItemAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = 0

    abstract fun setOnItemClickListener(listener: OnItemClickListener)
    abstract fun submitList(data: List<T>)
}
package com.arthe100.arshop.views.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.views.interfaces.OnItemClickListener

abstract class BaseItemViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView)  {
    abstract fun bind(data: T, listener: OnItemClickListener<T>)
    open fun bind (data: T , listeners: Map<String , OnItemClickListener<T>>){}
}
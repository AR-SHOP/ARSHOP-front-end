package com.arthe100.arshop.views.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.views.adapters.OnItemClickListener

abstract class BaseItemViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView)  {
    abstract fun bind(data: T, listener: OnItemClickListener)
    open fun bind (data: T , listeners: Map<String , OnItemClickListener>){}
}
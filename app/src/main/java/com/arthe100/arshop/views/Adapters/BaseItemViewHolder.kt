package com.arthe100.arshop.views.Adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseItemViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView)  {
    abstract fun bind(data: T, listener: OnItemClickListener)
}
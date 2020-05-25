package com.arthe100.arshop.views.interfaces

interface OnItemClickListener<T> {
    fun onItemClick(data: T)
    fun onItemClick(position: Int)
}
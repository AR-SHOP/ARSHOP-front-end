package com.arthe100.arshop.views.adapters.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthe100.arshop.views.interfaces.ISetViewType
import com.arthe100.arshop.views.interfaces.OnItemClickListener
import com.smarteist.autoimageslider.SliderViewAdapter

abstract class GenericSliderAdapter<T>
    : SliderViewAdapter<SliderViewAdapter.ViewHolder>(), ISetViewType<T> {

    private var dataList = mutableListOf<T>()
    private var itemClickListener: OnItemClickListener<T>? = null
    private var diffUtil: GenericItemDiff<T>? = null
    private var viewType: Int = 0

    override fun setViewType(type: Int) {
        viewType = type
    }

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        return getViewHolder(
            LayoutInflater.from(parent?.context)
                .inflate(viewType, parent, false)
            , viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        (holder as? GenericSliderAdapter.Binder<T>)?.bind(dataList[position] , itemClickListener)
    }

    protected open fun getViewHolder(view: View, viewType: Int): SliderViewAdapter.ViewHolder {
        return SliderViewHolderFactory.create(view, viewType)
    }

    override fun getCount(): Int = dataList.size

    fun addItems(data: List<T>){
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    fun setItemListener(itemClickListener: OnItemClickListener<T>) {
        this.itemClickListener = itemClickListener
    }

    internal interface Binder<T> {
        fun bind(data: T , clickListener: OnItemClickListener<T>?)
    }
}
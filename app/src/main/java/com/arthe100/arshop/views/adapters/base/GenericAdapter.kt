package com.arthe100.arshop.views.adapters.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class GenericAdapter <T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList = mutableListOf<T>()
    private var itemClickListener: OnItemClickListener<T>? = null
    private var diffUtil: GenericDiffUtil<T>? = null

    protected abstract fun getLayoutId(position: Int, obj: T) : Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
            , viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? Binder<T>)?.bind(dataList[position] , itemClickListener)
    }

    protected open fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderFactory.create(view, viewType)
    }

    override fun getItemCount(): Int = dataList.size

    fun addItems(data: List<T>){

        if(diffUtil != null)
        {
            val result = DiffUtil.calculateDiff(diffUtil!!)
            dataList.clear()
            dataList.addAll(data)
            result.dispatchUpdatesTo(this)
            return
        }

        dataList = data.toMutableList()
        notifyDataSetChanged()
    }

    fun setDiffUtil(diffUtil: GenericDiffUtil<T>){
        this.diffUtil = diffUtil
    }

    internal interface Binder<T> {
        fun bind(data: T , clickListener: OnItemClickListener<T>?)
    }

}

interface OnItemClickListener<T> {
    fun onClickItem(data: T)
}
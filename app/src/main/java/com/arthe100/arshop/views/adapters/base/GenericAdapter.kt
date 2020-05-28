package com.arthe100.arshop.views.adapters.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class GenericAdapter <T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList = mutableListOf<T>()
    private var itemClickListener: OnItemClickListener<T>? = null
    private var diffUtil: GenericItemDiff<T>? = null
    private var viewListeners: List<ViewListeners<T>>? = null

    protected abstract fun getLayoutId(position: Int, obj: T) : Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
            , viewType)
    }

    override fun getItemViewType(position: Int): Int = getLayoutId(position , dataList[position])

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is Binder<*>)
            (holder as? Binder<T>)?.bind(dataList[position] , itemClickListener)
        else if(holder is BinderMultiple<*>)
            (holder as? BinderMultiple<T>)?.bind(dataList[position] , itemClickListener , viewListeners)
    }

    protected open fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderFactory.create(view, viewType)
    }

    override fun getItemCount(): Int = dataList.size

    fun addItems(data: List<T>){

        if(diffUtil != null)
        {
            val result = DiffUtil.calculateDiff(
                GenericDiffUtil(
                    oldItems = dataList ,
                    newItems = data ,
                    itemDiff = diffUtil!!))
            dataList.clear()
            dataList.addAll(data)
            result.dispatchUpdatesTo(this)
            return
        }

        dataList = data.toMutableList()
        notifyDataSetChanged()
    }

    fun setDiffUtil(diffUtil: GenericItemDiff<T>){
        this.diffUtil = diffUtil
    }
    fun setItemListener(itemClickListener: OnItemClickListener<T>) {
        this.itemClickListener = itemClickListener
    }
    fun setViewListeners(viewListeners: List<ViewListeners<T>>) {
        this.viewListeners = viewListeners
    }

    internal interface Binder<T> {
        fun bind(data: T , clickListener: OnItemClickListener<T>?)
    }

    internal interface BinderMultiple<T> {
        fun bind(data: T , itemListener: OnItemClickListener<T>?, viewListeners: List<ViewListeners<T>>?)
    }

}

interface OnItemClickListener<T> {
    fun onClickItem(data: T)
}
interface OnItemClickListenerForView<T> {
    fun onClickItem(data: T , position: Int)
}

data class ViewListeners<T>(
    val id: Int,
    val listener: OnItemClickListenerForView<T>?)
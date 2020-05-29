package com.arthe100.arshop.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Category
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.views.adapters.base.OnItemClickListener

//class GroupRecyclerViewAdapter : BaseItemAdapter<Category>() {
//
//    var products: MutableList<Product> = arrayListOf()
//
//    private lateinit var context: Context
//    private var dataList : ArrayList<Category> = arrayListOf()
//    private lateinit var mListener: OnItemClickListener<Category>
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        context = parent.context
//        return GroupRecyclerViewHolder(
//            LayoutInflater.from(context)
//                .inflate(R.layout.horizontal_product_recycler_view, parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (holder) {
//            is GroupRecyclerViewHolder -> {
//                holder.context = context
//                holder.productList = products
//                holder.bind(dataList[position], mListener)
//            }
//        }
//    }
//
//    override fun getItemCount(): Int = dataList.size
//
//    override fun setOnItemClickListener(listener: OnItemClickListener<Category>) {
//        mListener = listener
//    }
//
//    override fun submitList(data: MutableList<Category>) {
//        val result = DiffUtil.calculateDiff(object: DiffUtil.Callback() {
//            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//                return dataList[oldItemPosition].id ==
//                        data[newItemPosition].id
//            }
//
//            override fun getOldListSize(): Int {
//                return dataList.size
//            }
//
//            override fun getNewListSize(): Int {
//                return data.size
//            }
//
//            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//                val old = dataList[oldItemPosition]
//                val new = data[newItemPosition]
//                return old == new
//            }
//
//        })
//        dataList.clear()
//        dataList.addAll(data)
//        result.dispatchUpdatesTo(this)
//        notifyDataSetChanged()
//
//    }
//
//}
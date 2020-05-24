package com.arthe100.arshop.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arthe100.arshop.R
import com.arthe100.arshop.models.HomeSales
import com.smarteist.autoimageslider.SliderView
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter : SliderViewAdapter<SliderViewAdapter.ViewHolder>(){

    private var dataList: ArrayList<HomeSales> = arrayListOf()
    private lateinit var mListener: View.OnClickListener
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        context = parent!!.context
        return SliderViewHolder(
            LayoutInflater.from(parent?.context)
                .inflate(R.layout.discount_card_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        when(holder) {
            is SliderViewHolder -> {
                holder.bind(dataList[position])
                holder.itemView.setOnClickListener(mListener)
            }
        }
    }

    override fun getCount(): Int = dataList.size

    fun setOnItemClickListener(listener: View.OnClickListener) {
        mListener = listener
    }

    fun submitList(data: MutableList<HomeSales>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }


}
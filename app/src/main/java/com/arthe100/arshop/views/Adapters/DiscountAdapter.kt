package com.arthe100.arshop.views.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R

class DiscountAdapter : BaseItemAdapter<String>() {
    private lateinit var mListener: OnItemClickListener
    lateinit var imageList: List<String>
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        return DiscountViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.discount_card_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is DiscountViewHolder -> {
                holder.bind(imageList[position], mListener)
            }
        }

    }
    override fun getItemCount(): Int = imageList.size

    override fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun submitList(discountImages: List<String>){
        imageList = discountImages
    }

}
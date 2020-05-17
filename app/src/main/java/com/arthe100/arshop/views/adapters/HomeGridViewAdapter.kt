package com.arthe100.arshop.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.product_grid_item.view.*

class HomeGridViewAdapter(val context: Context) : BaseAdapter() {

    var dataList: MutableList<Product> = mutableListOf()
    lateinit var inflater: LayoutInflater

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {

        inflater = LayoutInflater.from(context)

        var cardView = inflater.inflate(R.layout.product_grid_item, viewGroup, false)
        var imageView = cardView.product_image
        var name = cardView.product_name
        var price = cardView.product_price


        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_white_background)
            .error(R.drawable.ic_white_background)


        Glide.with(context)
            .applyDefaultRequestOptions(requestOptions)
            .load(dataList[position].thumbnail)
            .into(imageView)

        name.text = dataList[position].name
        price.text = dataList[position].price.toString()

        return cardView
    }

    override fun getItem(position: Int): Product {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int = dataList.size

    fun submitList(data: MutableList<Product>) {

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
        dataList.clear()
        dataList.addAll(data)
//        result.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }
}
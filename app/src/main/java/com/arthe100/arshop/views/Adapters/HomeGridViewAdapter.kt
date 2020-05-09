package com.arthe100.arshop.views.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.product_grid_item.view.*

class HomeGridViewAdapter(val context: Context) : BaseAdapter() {

    var dataList: List<Product> = arrayListOf()

    lateinit var inflater: LayoutInflater

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var cardView = inflater.inflate(R.layout.product_grid_item, null)
        var imageView = cardView.product_image
        var name = cardView.product_name
        var price = cardView.product_price


        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

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

    fun submitList(data: List<Product>) {
        dataList = data
    }
}
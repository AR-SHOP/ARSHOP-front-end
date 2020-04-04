package com.arthe100.arshop.scripts

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.models.Data
import kotlinx.android.synthetic.main.data_layout.view.*


internal class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var dataTitle: TextView? = itemView.data_title
    private var dataDescription: TextView? = itemView.data_text
    private var dataImageUrl = itemView.image_view
    private var dataPrice: TextView? = itemView.data_price

    fun bind(data: Data) {
        dataTitle?.text = data.title
        dataDescription?.text = data.description
        dataPrice?.text = data.price.toString()

        //the image binding is up to the backend team
    }

}
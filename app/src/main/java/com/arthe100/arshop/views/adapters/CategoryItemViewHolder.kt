package com.arthe100.arshop.views.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.category_card_item.view.*

class CategoryItemViewHolder(itemView: View) : BaseItemViewHolder<Category>(itemView) {


    private var categoryName = itemView.category_name
    private var categoryImage = itemView.category_image

    override fun bind(data: Category, listener: OnItemClickListener) {

        categoryName.text = data.title
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)


        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(data.imageUrl)
            .into(categoryImage)


        itemView.setOnClickListener{
            var position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }


    }

    override fun bind(data: Category, listeners: Map<String, OnItemClickListener>) {
        super.bind(data, listeners)
    }
}
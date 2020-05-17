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
            .placeholder(R.drawable.ic_white_background)
            .error(R.drawable.ic_white_background)


        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(data.image)
            .into(categoryImage)


        itemView.setOnClickListener{
            var position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }


    }
}
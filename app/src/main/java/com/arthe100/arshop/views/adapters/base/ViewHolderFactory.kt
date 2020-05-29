package com.arthe100.arshop.views.adapters.base

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.CartItem
import com.arthe100.arshop.models.Category
import com.arthe100.arshop.models.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.cart_item.view.*
import kotlinx.android.synthetic.main.product_fragment_layout.view.cart_count_text
import kotlinx.android.synthetic.main.category_card_item.view.*
import kotlinx.android.synthetic.main.horizontal_product_recycler_view.view.*
import kotlinx.android.synthetic.main.product_grid_item.view.*

object ViewHolderFactory {
    fun create(view: View, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.product_grid_item -> ProductGridViewHolder(view)
            R.layout.cart_item -> CartItemViewHolder(view)
            R.layout.category_card_item -> CategoryItemViewHolder(view)
            R.layout.horizontal_product_recycler_view -> NestedRecyclerViewHolder(view)
//            R.layout.user_comment_card_item -> CommentRecyclerViewHolder(view)
            else -> throw IllegalArgumentException("Wrong view type")
        }
    }
}

class ProductGridViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView) , GenericAdapter.Binder<Product> {
    override fun bind(data: Product, clickListener: OnItemClickListener<Product>?) {
        itemView.apply {
            itemView.product_name.text = data.name
            itemView.product_price.text = data.price.toString()
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background)

            Glide.with(context)
                .applyDefaultRequestOptions(requestOptions)
                .load(data.thumbnail)
                .into(itemView.product_image)

            setOnClickListener{ clickListener?.onClickItem(data) }
        }
    }
}


class CategoryItemViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView) , GenericAdapter.Binder<Category> {

    private var categoryName = itemView.category_name
    private var categoryImage = itemView.category_image

    override fun bind(data: Category, clickListener: OnItemClickListener<Category>?) {

        itemView.apply {
            categoryName.text = data.title

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background)
            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(data.image)
                .into(categoryImage)

            setOnClickListener{ clickListener?.onClickItem(data) }
        }
    }
}

class NestedRecyclerViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), GenericAdapter.Binder<Category> {

    private lateinit var productAdapter: GenericAdapter<Product>
    private var categoryName = itemView.recycler_view_category_name
    private var productsRecyclerView = itemView.products_recycler_view


    override fun bind(data: Category, clickListener: OnItemClickListener<Category>?) {

        productAdapter = object: GenericAdapter<Product>() {
            override fun getLayoutId(position: Int, obj: Product): Int = R.layout.product_grid_item
        }

        productAdapter.apply {
            setDiffUtil(object: GenericItemDiff<Product> {
                override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem.id == newItem.id
                override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem == newItem
            })
            setItemListener(object:
                OnItemClickListener<Product> {
                override fun onClickItem(data: Product) {
                    TODO("Not yet implemented")
                }
            })
        }

        itemView.apply {
            categoryName.text = data.title
            productsRecyclerView.apply {
                layoutManager = LinearLayoutManager(this.context,
                    RecyclerView.HORIZONTAL, false)
                adapter = productAdapter
            }
            setOnClickListener{ clickListener?.onClickItem(data) }
        }
    }
}

class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , GenericAdapter.BinderMultiple<CartItem>{

    override fun bind(
        data: CartItem,
        itemListener: OnItemClickListener<CartItem>?,
        viewListeners: List<ViewListeners<CartItem>>?
    ) {
        itemView.apply {

            val product = data.product

            itemView.cart_item_name.text = product.name
            itemView.cart_item_price.text = product.price.toString()
            itemView.cart_count_text.text = data.quantity.toString()
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(product.thumbnail)
                .into(itemView.cart_item_image)

            viewListeners?.forEach {
                val view = itemView.findViewById(it.id) as View
                view.setOnClickListener { _ ->
                    it.listener?.onClickItem(data , adapterPosition)
                }
            }

            setOnClickListener { itemListener?.onClickItem(data) }
        }
    }


}

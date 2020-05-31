package com.arthe100.arshop.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.CartItem
import com.arthe100.arshop.views.adapters.base.OnItemClickListener

class CartItemAdapter(dataList: List<CartItem>) : BaseItemAdapter<CartItem>() {

    private lateinit var mListener: OnItemClickListener<CartItem>
    lateinit var plusListener: OnItemClickListener<CartItem>
    lateinit var minusListener: OnItemClickListener<CartItem>
    lateinit var deleteListener: OnItemClickListener<CartItem>
    val dataList: MutableList<CartItem> = mutableListOf()
    val items: List<CartItem>
        get() = dataList

    init {
        this.dataList.addAll(dataList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CartItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is CartItemViewHolder -> {

                val listeners= mapOf(
                    "main" to mListener,
                    "add" to plusListener,
                    "minus" to minusListener,
                    "delete" to deleteListener
                )
                holder.bind(dataList[position], listeners)
//                plus_btn.visibility = View.VISIBLE
//                cart_count_text.visibility = View.VISIBLE
//                minus_btn.visibility = View.VISIBLE
//                delete_btn.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun setOnItemClickListener(listener: OnItemClickListener<CartItem>) {
        mListener = listener
    }



    override fun submitList(data: MutableList<CartItem>) {

        val result = DiffUtil.calculateDiff(object: DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return dataList[oldItemPosition].product.id ==
                        data[newItemPosition].product.id
            }

            override fun getOldListSize(): Int {
                return dataList.size
            }

            override fun getNewListSize(): Int {
                return data.size
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val old = dataList[oldItemPosition]
                val new = data[newItemPosition]
                return old.quantity == new.quantity
            }

        })
        dataList.clear()
        dataList.addAll(data)
        result.dispatchUpdatesTo(this)
    }
}

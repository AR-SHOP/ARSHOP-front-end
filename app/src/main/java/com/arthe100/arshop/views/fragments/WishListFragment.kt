package com.arthe100.arshop.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.base.GenericAdapter
import com.arthe100.arshop.views.adapters.base.GenericItemDiff
import com.arthe100.arshop.views.interfaces.ILoadFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.fragment_wish_list.*
import javax.inject.Inject

class WishListFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory
    ) : BaseFragment(), ILoadFragment {

    private lateinit var wishListAdapter: GenericAdapter<Product>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar?.visibility = View.INVISIBLE
        return inflater.inflate(R.layout.fragment_wish_list, container, false)
    }

    override fun render(state: ViewState) {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart()
        setRecyclerView()
    }


    private fun setAdapter() {
        wishListAdapter = object : GenericAdapter<Product>() {
            override fun getLayoutId(position: Int, obj: Product): Int = R.layout.item_wish_list
        }

        wishListAdapter.apply {
            setDiffUtil(object : GenericItemDiff<Product> {
                override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                    return oldItem == newItem
                }
            })
        }
    }

    private fun setRecyclerView() {
        if (!this::wishListAdapter.isInitialized) setAdapter()
        wish_list_recycler_view?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = wishListAdapter
        }
    }

}

package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Category
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.CategoryItemAdapter
import com.arthe100.arshop.views.adapters.OnItemClickListener
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.categories_fragment_layout.*

class CategoriesFragment : BaseFragment() {


//    private var list: MutableList<Category> = arrayListOf(
//        Category("دسته بندی ۱",
//            "https://image.shutterstock.com/image-vector/discount-banner-shape-sale-50-600w-1542205283.jpg"),
//        Category("دسته بندی ۲",
//        "https://image.shutterstock.com/image-vector/special-offer-final-sale-banner-600w-1387497926.jpg")
//    )

    private lateinit var categoryItemAdapter: CategoryItemAdapter

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        return inflater.inflate(R.layout.categories_fragment_layout, container, false)
    }

    override fun onStart() {
        setSearchView()

        super.onStart()
    }


    override fun toString(): String {
        return "Categories"
    }

    private fun setSearchView() {
        categories_search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun addCategories(data: MutableList<Category>) {
        categoryItemAdapter.submitList(data)
    }

    private fun setRecyclerView() {

        categoryItemAdapter = CategoryItemAdapter()
        categoryItemAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                TODO("Not yet implemented")
            }
        })

        categories_recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoryItemAdapter
        }
    }
}

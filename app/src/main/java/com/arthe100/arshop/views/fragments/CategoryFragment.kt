package com.arthe100.arshop.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.mvi.Products.ProductUiAction
import com.arthe100.arshop.scripts.mvi.Products.ProductViewModel
import com.arthe100.arshop.scripts.mvi.categories.CategoryViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.HomeGridViewAdapter
import kotlinx.android.synthetic.main.category_fragment_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import javax.inject.Inject

class CategoryFragment : BaseFragment() {

    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var fragmentFactory: FragmentFactory

    private lateinit var gridViewAdapter: HomeGridViewAdapter
    private lateinit var model: CategoryViewModel
    private lateinit var productViewModel: ProductViewModel

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(CategoryViewModel::class.java)
        productViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(ProductViewModel::class.java)
        return inflater.inflate(R.layout.category_fragment_layout, container, false)
    }


    override fun onStart() {
        setSearchView()
        setGridView()
        addProducts(model.products)
        super.onStart()
    }

    override fun toString(): String {
        return "Category"
    }

    //add the products of the given category, the subcategories are not yet implemented
    private fun addProducts(data: List<Product>) {
        gridViewAdapter.submitList(data)
    }

    private fun setGridView() {
        gridViewAdapter = HomeGridViewAdapter(requireContext())

        category_grid_view.setOnItemClickListener { _, _, pos, _ ->
            productViewModel.product = gridViewAdapter.dataList[pos]
            loadFragment(fragmentFactory.create<ProductFragment>())
        }

        category_grid_view.apply {
            adapter = gridViewAdapter
        }
    }

    private fun setSearchView() {
        category_search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }
}

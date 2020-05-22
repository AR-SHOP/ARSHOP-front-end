package com.arthe100.arshop.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.mvi.Products.ProductUiAction
import com.arthe100.arshop.scripts.mvi.Products.ProductViewModel
import com.arthe100.arshop.scripts.mvi.categories.CategoryState
import com.arthe100.arshop.scripts.mvi.categories.CategoryUiAction
import com.arthe100.arshop.scripts.mvi.categories.CategoryViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.HomeGridViewAdapter
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.categories_fragment_layout.*
import kotlinx.android.synthetic.main.category_fragment_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import javax.inject.Inject

class CategoryFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val dialogBoxManager: DialogBoxManager

) : BaseFragment() {



    private lateinit var gridViewAdapter: HomeGridViewAdapter
    private lateinit var model: CategoryViewModel
    private lateinit var productViewModel: ProductViewModel
    private val currentObserver = Observer(::render)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(CategoryViewModel::class.java)
        productViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(ProductViewModel::class.java)
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        return inflater.inflate(R.layout.category_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentViewState.observe(requireActivity() ,currentObserver)
    }


    override fun onStart() {
        category_name.text = model.currentCategory.info.title
        setSearchView()
        setGridView()
        addProducts(model.currentCategory.products)

        category_swipe_refresh_layout.isRefreshing = true
        category_swipe_refresh_layout.setOnRefreshListener {
            model.onEvent(CategoryUiAction.GetCategoryProduct(model.currentCategory.info))
        }
//        setGridView()
//        addProducts(model.products)
//        model.onEvent(CategoryUiAction.GetCategoryProduct(model.currentCategory))
        category_swipe_refresh_layout.isRefreshing = false
        super.onStart()
    }

    override fun toString(): String {
        return "Category"
    }

    private fun render(state: CategoryState){
        when(state){
            is CategoryState.IdleState -> {
                dialogBoxManager.cancel()
                category_swipe_refresh_layout?.isRefreshing = false
            }
            is CategoryState.LoadingState -> {
                category_swipe_refresh_layout?.isRefreshing = true
            }
            is CategoryState.GetProductSuccess -> {
                dialogBoxManager.cancel()
                model.setProducts(state.products)
                addProducts(state.products)
            }
            is CategoryState.Failure -> {
                dialogBoxManager.cancel()
                if (context == null) return
                dialogBoxManager.showDialog(requireContext(),MessageType.ERROR)
            }
        }
    }


    private fun addProducts(data: List<Product>) {
        val list = mutableListOf<Product>()
        list.addAll(data)
        gridViewAdapter.submitList(list)
    }

    private fun setGridView() {
        gridViewAdapter = HomeGridViewAdapter(requireContext())

        category_grid_view.setOnItemClickListener { _, _, pos, _ ->
            productViewModel.product = gridViewAdapter.dataList[pos]
            loadFragment(ProductFragment::class.java)
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

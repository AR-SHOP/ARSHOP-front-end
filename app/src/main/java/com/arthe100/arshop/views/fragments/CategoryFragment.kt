package com.arthe100.arshop.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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
import com.arthe100.arshop.views.adapters.base.GenericAdapter
import com.arthe100.arshop.views.adapters.base.GenericItemDiff
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import com.arthe100.arshop.views.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.categories_fragment_layout.*
import kotlinx.android.synthetic.main.category_fragment_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import javax.inject.Inject

class CategoryFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val dialogBoxManager: DialogBoxManager

) : BaseFragment() {



    private lateinit var gridViewAdapter: GenericAdapter<Product>
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
        super.onStart()
        category_name.text = model.currentCategory.info.title
        setSearchView()
        setGridView()
//        addProducts(model.currentCategory.products)
        model.onEvent(CategoryUiAction.GetCategoryProduct(model.currentCategory.info))
    }

    override fun toString(): String {
        return "Category"
    }

    private fun render(state: CategoryState){
        when(state){
            is CategoryState.IdleState -> {
                dialogBoxManager.cancel()
            }
            is CategoryState.LoadingState -> {
                dialogBoxManager.cancel()
            }
            is CategoryState.GetProductSuccess -> {
                dialogBoxManager.cancel()
                model.setProducts(state.products)
                gridViewAdapter.addItems(state.products)
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
        gridViewAdapter.addItems(list)
    }

    private fun setAdapter() {
        gridViewAdapter = object: GenericAdapter<Product>() {
            override fun getLayoutId(position: Int, obj: Product): Int = R.layout.product_grid_item
        }

        gridViewAdapter.apply {
            setDiffUtil(object : GenericItemDiff<Product> {
                override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                    oldItem == newItem
            })
            setItemListener(object :
                OnItemClickListener<Product> {
                override fun onItemClick(data: Product) {
                    productViewModel.product = data
                    loadFragment(ProductFragment::class.java)
                }

                override fun onItemClick(position: Int) {
                    TODO("Not yet implemented")
                }
            })
        }

    }

    private fun setGridView() {

        if(!this::gridViewAdapter.isInitialized) setAdapter()
        category_group_recycler_view?.apply {
            layoutManager = GridLayoutManager(requireContext() , 2)
            adapter = gridViewAdapter
            isNestedScrollingEnabled = false
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

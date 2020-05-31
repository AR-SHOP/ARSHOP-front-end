package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Category
import com.arthe100.arshop.scripts.mvi.categories.CategoryState
import com.arthe100.arshop.scripts.mvi.categories.CategoryUiAction
import com.arthe100.arshop.scripts.mvi.categories.CategoryViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.base.GenericAdapter
import com.arthe100.arshop.views.adapters.base.GenericItemDiff
import com.arthe100.arshop.views.adapters.base.OnItemClickListener
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.categories_fragment_layout.*
import javax.inject.Inject

class CategoriesFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val dialogBoxManager: DialogBoxManager
) : BaseFragment() {

    private lateinit var model: CategoryViewModel
    private lateinit var categoryItemAdapter: GenericAdapter<Category>
    private val currentObserver = Observer(::render)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(CategoryViewModel::class.java)
        return inflater.inflate(R.layout.categories_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSearchView()
        setRecyclerView()
        model.currentViewState.observe(requireActivity() ,currentObserver)
    }

    override fun onStart() {
        categories_swipe_refresh?.isRefreshing = true
        categories_swipe_refresh?.setOnRefreshListener {
            model.onEvent(CategoryUiAction.GetCategories)
            categories_swipe_refresh?.isRefreshing = false
        }
        model.onEvent(CategoryUiAction.GetCategories)
        categories_swipe_refresh?.isRefreshing = false
        super.onStart()
    }


    private fun render(state: CategoryState){
        when(state){
            is CategoryState.IdleState -> {
                dialogBoxManager.cancel()
                categories_swipe_refresh?.isRefreshing = false
            }
            is CategoryState.LoadingState -> {
                categories_swipe_refresh?.isRefreshing = true
            }
            is CategoryState.GetCategorySuccess -> {
                model.categories = state.categories
                dialogBoxManager.cancel()
                categories_swipe_refresh?.isRefreshing = false
                categoryItemAdapter.addItems(state.categories)
            }
            is CategoryState.GetProductSuccess -> {
                dialogBoxManager.cancel()
                categories_swipe_refresh?.isRefreshing = false
                model.setProducts(state.products)
                loadFragment(CategoryFragment::class.java)
            }
            is CategoryState.Failure -> {
                dialogBoxManager.cancel()
                categories_swipe_refresh?.isRefreshing = false
            }
        }
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

//    private fun addCategories(data: List<Category>) {
//        if(!this::categoryItemAdapter.isInitialized) setRecyclerView()
//        val list = mutableListOf<Category>()
//        list.addAll(data)
//        categoryItemAdapter.submitList(list)
//    }

    private fun setRecyclerView() {
        if(!this::categoryItemAdapter.isInitialized) setAdapter()
        categories_recyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoryItemAdapter
        }
    }


    private fun setAdapter() {

        categoryItemAdapter = object: GenericAdapter<Category>() {
            override fun getLayoutId(position: Int, obj: Category): Int = R.layout.category_card_item
        }

        categoryItemAdapter.apply {
            setDiffUtil(object: GenericItemDiff<Category> {
                override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean = oldItem.id == newItem.id
                override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean = oldItem == newItem
            })
            setItemListener(object:
                OnItemClickListener<Category> {
                override fun onClickItem(data: Category) {
                    model.onEvent(CategoryUiAction.GetCategoryProduct(data))
                }

            })
        }
    }
}

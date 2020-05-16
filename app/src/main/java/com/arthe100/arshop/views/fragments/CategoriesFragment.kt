package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Category
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.mvi.categories.CategoryState
import com.arthe100.arshop.scripts.mvi.categories.CategoryUiAction
import com.arthe100.arshop.scripts.mvi.categories.CategoryViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.CategoryItemAdapter
import com.arthe100.arshop.views.adapters.OnItemClickListener
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.categories_fragment_layout.*
import javax.inject.Inject

class CategoriesFragment : BaseFragment() {

    @Inject lateinit var categoryFragment: CategoryFragment
    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var dialogBoxManager: DialogBoxManager

    private lateinit var model: CategoryViewModel
    private lateinit var categoryItemAdapter: CategoryItemAdapter

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(CategoryViewModel::class.java)
        model.currentViewState.observe(requireActivity() , Observer(::render))
        return inflater.inflate(R.layout.categories_fragment_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
        setSearchView()
        model.onEvent(CategoryUiAction.GetCategories)
    }


    private fun render(state: CategoryState){
        when(state){
            is CategoryState.IdleState -> {
                dialogBoxManager.cancel()
            }
            is CategoryState.LoadingState -> {
                dialogBoxManager.showDialog(requireContext() , MessageType.LOAD)
            }
            is CategoryState.GetCategorySuccess -> {
                dialogBoxManager.cancel()
                setRecyclerView()
                addCategories(state.categories)
            }
            is CategoryState.Failure -> {
                dialogBoxManager.cancel()
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

    private fun addCategories(data: List<Category>) {
        if(!this::categoryItemAdapter.isInitialized) setRecyclerView()
        val list = mutableListOf<Category>()
        list.addAll(data)
        categoryItemAdapter.submitList(list)
    }

    private fun setRecyclerView() {

        categoryItemAdapter = CategoryItemAdapter()
        categoryItemAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                loadFragment(categoryFragment)
            }
        })

        categories_recyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoryItemAdapter
        }
    }
}

package com.arthe100.arshop.views.fragments

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CursorAdapter
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.mvi.Products.ProductViewModel
import com.arthe100.arshop.scripts.mvi.base.CategoryState
import com.arthe100.arshop.scripts.mvi.base.CategoryUiAction
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.scripts.mvi.categories.CategoryViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.base.GenericAdapter
import com.arthe100.arshop.views.adapters.base.GenericItemDiff
import com.arthe100.arshop.views.adapters.base.OnItemClickListener
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import kotlinx.android.synthetic.main.activity_main_layout.*
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

    override fun render(state: ViewState){
        when(state){
            is ViewState.IdleState -> {
                dialogBoxManager.cancel()
            }
            is ViewState.LoadingState -> {
                dialogBoxManager.cancel()
            }
            is CategoryState.GetProductSuccess -> {
                dialogBoxManager.cancel()
                model.setProducts(state.products)
                gridViewAdapter.addItems(state.products)
            }
            is ViewState.Failure -> {
                dialogBoxManager.cancel()
                if (context == null) return
                dialogBoxManager.showDialog(requireContext(),MessageType.ERROR , state.throwable.toString())
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
                override fun onClickItem(data: Product) {
                    productViewModel.product = data
                    loadFragment(ProductFragment::class.java)
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

        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.suggestion_text)
        val cursorAdapter = SimpleCursorAdapter(context, R.layout.item_suggestion, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        val suggestions = listOf("Apple", "Appie" , "Appe" , "Blueberry", "Carrot", "Daikon")

        category_search_view?.suggestionsAdapter = cursorAdapter



        category_search_view?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                newText?.let {
                    suggestions.forEachIndexed { index, suggestion ->
                        if (suggestion.contains(newText, true))
                            cursor.addRow(arrayOf(index, suggestion))
                    }
                }

                cursorAdapter.changeCursor(cursor)

                return true
            }
        })

        category_search_view?.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                hideKeyboard()
                val cursor = category_search_view?.suggestionsAdapter?.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                category_search_view?.setQuery(selection, false)

                // Do something with selection
                return true
            }
        })
    }


    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun Fragment.hideKeyboard() {
        view?.let {
            activity?.hideKeyboard(it)
        }
    }

}

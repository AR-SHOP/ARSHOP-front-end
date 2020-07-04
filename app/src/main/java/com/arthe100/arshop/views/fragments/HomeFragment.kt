package com.arthe100.arshop.views.fragments

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.inputmethod.InputMethodManager
import android.widget.CursorAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.arthe100.arshop.R
import com.arthe100.arshop.models.HomeSales
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.Products.ProductViewModel
import com.arthe100.arshop.scripts.mvi.base.*
import com.arthe100.arshop.scripts.mvi.cart.CartViewModel
import com.arthe100.arshop.scripts.mvi.home.HomeViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.interfaces.ILoadFragment
import com.arthe100.arshop.views.adapters.base.GenericAdapter
import com.arthe100.arshop.views.adapters.base.GenericItemDiff
import com.arthe100.arshop.views.adapters.base.OnItemClickListener
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import com.arthe100.arshop.views.utility.CircleIndicator
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import javax.inject.Inject


class HomeFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val session: UserSession
): BaseFragment(), ILoadFragment , IRenderable{

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var productViewModel: ProductViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var messageManager: MessageManager
    private lateinit var homePageGrid: GenericAdapter<Product>
    private lateinit var dialogBox: DialogBoxManager
    private lateinit var snapHelper: PagerSnapHelper
    private lateinit var circleIndicator: CircleIndicator
//    private lateinit var suggestions: ArrayList<String>
//    private lateinit var groupAdapter: GroupRecyclerViewAdapter
    private lateinit var discountSliderViewAdapter: GenericAdapter<HomeSales>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        dialogBox = DialogBoxManager()
        messageManager = MessageManager()
        snapHelper = PagerSnapHelper()
        circleIndicator = CircleIndicator()
        productViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(ProductViewModel::class.java)
        homeViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(HomeViewModel::class.java)
        cartViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(CartViewModel::class.java)
        productViewModel.currentViewState.observe(viewLifecycleOwner , Observer(::render))
        homeViewModel.currentViewState.observe(viewLifecycleOwner , Observer(::render))
        return inflater.inflate(R.layout.home_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSearchView()
        setGroupRecyclerView()
        setDiscountRecyclerView()
//        setImageSlider()
        productViewModel.load(::render)
        homeViewModel.load(::render)
        productViewModel.onEvent(ProductUiAction.GetProducts)
        homeViewModel.onEvent(HomeUiAction.GetHomePageSales)
        when(session.user)
        {
            is User.User ->
                cartViewModel.onEvent(CartUiAction.GetCartOnStart)
        }

    }

    override fun toString(): String {
        return "Home"
    }

    private fun setSearchView() {

        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.suggestion_text)
        val cursorAdapter = SimpleCursorAdapter(context, R.layout.item_suggestion, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        val suggestions = listOf("Apple", "Appie" , "Appe" , "Blueberry", "Carrot", "Daikon")

        home_search_view?.suggestionsAdapter = cursorAdapter

        home_search_view?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

        home_search_view?.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                hideKeyboard()
                val cursor = home_search_view?.suggestionsAdapter?.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                home_search_view?.setQuery(selection, false)

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

    override fun render(state: ViewState){
        when(state){
            is ViewState.IdleState -> dialogBox.cancel()
            is ViewState.LoadingState -> dialogBox.showDialog(requireActivity(), MessageType.LOAD)
            is ViewState.Failure -> {

                dialogBox.showDialog(requireContext(), MessageType.ERROR, state.throwable.toString())}
            is ProductState.ProductsSuccess -> {
                productViewModel.currentProducts = state.products
                dialogBox.cancel()
                homePageGrid.addItems(state.products)
            }
            is HomeState.HomePageSalesSuccess -> {
                homeViewModel.currentSales = state.sales
                addDiscounts(state.sales)
            }
            is ProductState.ProductDetailSuccess -> {
                dialogBox.cancel()
                loadFragment(ProductFragment::class.java)
            }
        }
    }




    private fun addDiscounts(discounts: List<HomeSales>) {
        val newList = mutableListOf<HomeSales>()
        newList.addAll(discounts.filter { it.id > 4 })
        discountSliderViewAdapter.addItems(newList)
    }

    private fun setDiscountSliderAdapter() {

        discountSliderViewAdapter = object : GenericAdapter<HomeSales>() {
            override fun getLayoutId(position: Int, obj: HomeSales): Int = R.layout.discount_card_view
        }

        discountSliderViewAdapter.apply {
            setDiffUtil(object : GenericItemDiff<HomeSales> {
                override fun areItemsTheSame(oldItem: HomeSales, newItem: HomeSales): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: HomeSales, newItem: HomeSales): Boolean =
                    oldItem == newItem
            })
            setItemListener(object :
                OnItemClickListener<HomeSales> {
                override fun onClickItem(data: HomeSales) {
                    //ignored
                }

            })
        }

//        discountSliderViewAdapter = object : GenericSliderAdapter<HomeSales>() {}
//        discountSliderViewAdapter.setViewType(R.layout.discount_card_view)
//
//        discountSliderViewAdapter.setItemListener(object :
//            OnItemClickListener<HomeSales> {
//            override fun onClickItem(data: HomeSales) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }

    private fun setDiscountRecyclerView() {
        if (!this::discountSliderViewAdapter.isInitialized) setDiscountSliderAdapter()
        snapHelper.attachToRecyclerView(discount_recycler_view)
        discount_recycler_view?.addItemDecoration(circleIndicator)

        discount_recycler_view?.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, true)
            adapter = discountSliderViewAdapter
        }
    }

//    private fun setImageSlider() {
//        if(!this::discountSliderViewAdapter.isInitialized) setSliderAdapter()
//        image_slider?.apply {
//            setSliderAdapter(discountSliderViewAdapter)
//            isAutoCycle = true
//            setIndicatorVisibility(true)
//            setIndicatorAnimation(IndicatorAnimations.SCALE)
//            setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
//            autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
//            startAutoCycle()
//        }
//    }

//    private fun setGroupRecyclerView() {
//        groupAdapter = GroupRecyclerViewAdapter()
//
//        groupAdapter.setOnItemClickListener(object : OnItemClickListener {
//            override fun onItemClick(position: Int) {
//                TODO("Not yet implemented")
//            }
//        })
//
//        group_recycler_view?.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = groupAdapter
//            isNestedScrollingEnabled = false
//        }
//    }


    private fun setAdapter() {
        homePageGrid = object: GenericAdapter<Product>() {
            override fun getLayoutId(position: Int, obj: Product): Int = R.layout.product_grid_item
        }
        homePageGrid.apply {
            setDiffUtil(object : GenericItemDiff<Product> {
                override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                    oldItem == newItem
            })
            setItemListener(object :
                OnItemClickListener<Product> {
                override fun onClickItem(data: Product) {
                    productViewModel.onEvent(ProductUiAction.GetProductDetailsOffline(data))
                }

            })
        }
    }

    private fun setGroupRecyclerView() {
        if(!this::homePageGrid.isInitialized) setAdapter()
        group_recycler_view?.apply {
            layoutManager = GridLayoutManager(requireContext() , 2)
            adapter = homePageGrid
            isNestedScrollingEnabled = false
        }
    }
}

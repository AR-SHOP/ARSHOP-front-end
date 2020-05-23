package com.arthe100.arshop.views.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Category
import com.arthe100.arshop.models.HomeSales
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.Products.ProductState
import com.arthe100.arshop.scripts.mvi.Products.ProductUiAction
import com.arthe100.arshop.scripts.mvi.Products.ProductViewModel
import com.arthe100.arshop.scripts.mvi.cart.CartUiAction
import com.arthe100.arshop.scripts.mvi.cart.CartViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.ILoadFragment
import com.arthe100.arshop.views.adapters.*
import com.arthe100.arshop.views.decorators.CircleIndicator
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import javax.inject.Inject


class HomeFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val session: UserSession
): BaseFragment(), ILoadFragment {

    private lateinit var model: ProductViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var messageManager: MessageManager
    private lateinit var gridViewAdapter: HomeGridViewAdapter
    private lateinit var dialogBox: DialogBoxManager
    private lateinit var snapHelper: PagerSnapHelper
    private lateinit var circleIndicator: CircleIndicator
    private lateinit var suggestions: ArrayList<String>
    private lateinit var groupAdapter: GroupRecyclerViewAdapter
    private lateinit var sliderViewAdapter: SliderAdapter



    private var categoryList =
        arrayListOf<Category>(
            Category(1,"name1","a", ""),
            Category(2,"name2","a", ""),
            Category(3,"name3","a", ""),
            Category(3,"name4","a", ""),
            Category(3,"name5","a", "")
        )

        private var products = arrayListOf<Product>(
        Product(1,"p1","a","b",123,"https://elcopcbonline.com/photos/product/4/176/4.jpg",""),
        Product(1,"p2","a","b",123,"https://elcopcbonline.com/photos/product/4/176/4.jpg",""),
        Product(1,"p3","a","b",123,"https://elcopcbonline.com/photos/product/4/176/4.jpg","")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        dialogBox = DialogBoxManager()
        messageManager = MessageManager()
        snapHelper = PagerSnapHelper()
        circleIndicator = CircleIndicator()
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(ProductViewModel::class.java)
        model.loadCache()
        cartViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(CartViewModel::class.java)
        return inflater.inflate(R.layout.home_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentViewState.observe(viewLifecycleOwner , Observer(::render))
    }

    override fun onStart() {
        setSearchView()
        model.onEvent(ProductUiAction.GetHomePageProducts)
        model.onEvent(ProductUiAction.GetHomePageSales)
        cartViewModel.onEvent(CartUiAction.GetCartOnStart)
        super.onStart()
    }


    override fun toString(): String {
        return "Home"
    }

    private fun setSearchView() {
        home_search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }

    private fun render(state: ProductState){
        when(state){
            is ProductState.Idle -> {
                dialogBox.cancel()
                requireView().visibility = View.VISIBLE
            }

            is ProductState.LoadingState -> {
                requireView().visibility = View.INVISIBLE
                dialogBox.showDialog(requireActivity(), MessageType.LOAD)
            }

            is ProductState.GetProductsSuccess -> {
                model.currentProducts = state.products
                dialogBox.cancel()
                requireView().visibility = View.VISIBLE
                setGroupRecyclerView()
                addProducts(products)
            }
            is ProductState.ProductDetailSuccess -> {
                dialogBox.cancel()
                requireView().visibility = View.VISIBLE
                loadFragment(ProductFragment::class.java)
            }

            is ProductState.GetProductsFailure -> {
                requireView().visibility = View.VISIBLE
                dialogBox.showDialog(requireContext(), MessageType.ERROR, "خطا در برقراری ارتباط با سرور")
            }
            is ProductState.HomePageSalesSuccess ->{
                model.currentSales = state.sales
                requireView().visibility = View.VISIBLE
                setImageSlider()
                addDiscounts(state.sales)
            }
            is ProductState.HomePageSalesFailure -> {
                requireView().visibility = View.VISIBLE
                dialogBox.showDialog(requireContext(), MessageType.ERROR, "خطا در برقراری ارتباط با سرور")
            }
        }

    }

    private fun addProducts(products: List<Product>) {
        groupAdapter.products.addAll(products)
        groupAdapter.submitList(categoryList)
    }

    private fun addDiscounts(discounts: List<HomeSales>) {
        if(!this::sliderViewAdapter.isInitialized) setImageSlider()
        val newList = mutableListOf<HomeSales>()
        newList.addAll(discounts.filter { it.id > 4 })
        sliderViewAdapter.submitList(newList)
    }

    private fun setImageSlider() {
        sliderViewAdapter = SliderAdapter()
        sliderViewAdapter.setOnItemClickListener(View.OnClickListener {
            Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
        })

        image_slider?.apply {
            setSliderAdapter(sliderViewAdapter)
            isAutoCycle = true
            setIndicatorAnimation(IndicatorAnimations.SCALE)
            setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
            autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
            startAutoCycle()
        }
    }

    private fun setGroupRecyclerView() {


        groupAdapter = GroupRecyclerViewAdapter()

        groupAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                TODO("Not yet implemented")
            }
        })

        group_recycler_view?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = groupAdapter
            isNestedScrollingEnabled = false
        }
    }

}

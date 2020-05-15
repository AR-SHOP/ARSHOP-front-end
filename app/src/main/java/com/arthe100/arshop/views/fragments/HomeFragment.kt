package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.arthe100.arshop.R
import com.arthe100.arshop.models.HomeSales
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.Products.ProductState
import com.arthe100.arshop.scripts.mvi.Products.ProductUiAction
import com.arthe100.arshop.scripts.mvi.Products.ProductViewModel
import com.arthe100.arshop.scripts.mvi.cart.CartUiAction
import com.arthe100.arshop.scripts.mvi.cart.CartViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.ILoadFragment
import com.arthe100.arshop.views.adapters.DiscountAdapter
import com.arthe100.arshop.views.adapters.HomeGridViewAdapter
import com.arthe100.arshop.views.adapters.OnItemClickListener
import com.arthe100.arshop.views.decorators.CircleIndicator
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import javax.inject.Inject


class HomeFragment: BaseFragment(), ILoadFragment {
    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var customArFragment: CustomArFragment
    @Inject lateinit var session: UserSession
    @Inject lateinit var productFragment: ProductFragment

    private lateinit var discountAdapter: DiscountAdapter
    private lateinit var model: ProductViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var messageManager: MessageManager
    private lateinit var gridViewAdapter: HomeGridViewAdapter
    private lateinit var dialogBox: DialogBoxManager
    private lateinit var snapHelper: PagerSnapHelper
    private lateinit var circleIndicator: CircleIndicator
    private lateinit var suggestions: ArrayList<String>

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        dialogBox = DialogBoxManager()
        messageManager = MessageManager()
        snapHelper = PagerSnapHelper()
        circleIndicator = CircleIndicator()
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(ProductViewModel::class.java)
        cartViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(CartViewModel::class.java)
        return inflater.inflate(R.layout.home_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentViewState.observe(viewLifecycleOwner , Observer(::render))
    }

    override fun onStart() {
        setSearchView()
        swipe_refresh_layout.setOnRefreshListener {
            model.onEvent(ProductUiAction.GetHomePageProducts)
            cartViewModel.onEvent(CartUiAction.GetCartOnStart)
            swipe_refresh_layout.isRefreshing = false
        }
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
                dialogBox.cancel()
                requireView().visibility = View.VISIBLE
                setGridView()
                addProducts(state.products)
            }
            is ProductState.ProductDetailSuccess -> {
                dialogBox.cancel()
                requireView().visibility = View.VISIBLE
                loadFragment(productFragment)
            }

            is ProductState.GetProductsFailure -> {
                requireView().visibility = View.VISIBLE
                dialogBox.showDialog(requireContext(), MessageType.ERROR, "خطا در برقراری ارتباط با سرور")
            }
            is ProductState.HomePageSalesSuccess ->{
                requireView().visibility = View.VISIBLE
                setRecyclerView()
                addDiscounts(state.sales)
            }
            is ProductState.HomePageSalesFailure -> {
                requireView().visibility = View.VISIBLE
                dialogBox.showDialog(requireContext(), MessageType.ERROR, "خطا در برقراری ارتباط با سرور")
            }
        }

    }

    private fun addProducts(data: List<Product>) {
        gridViewAdapter.submitList(data)
    }

    private fun addDiscounts(discounts: List<HomeSales>) {
        if(!this::discountAdapter.isInitialized) setRecyclerView()
        val newList = mutableListOf<HomeSales>()
        newList.addAll(discounts.filter { it.id > 4 })
        discountAdapter.submitList(newList)
    }

    private fun setRecyclerView() {
        discountAdapter = DiscountAdapter()
        discountAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(requireContext(),"Clicked", Toast.LENGTH_LONG).show()
            }
        })

        snapHelper.attachToRecyclerView(discount_recycler_view)
        discount_recycler_view?.addItemDecoration(circleIndicator)

        discount_recycler_view?.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, true)
            adapter = discountAdapter
        }
    }

    private fun setGridView() {
        gridViewAdapter = HomeGridViewAdapter(requireContext())

        home_grid_view?.setOnItemClickListener { _, _, pos, _ ->
            model.onEvent(ProductUiAction.GetProductDetails(gridViewAdapter.getItem(pos)))
        }

        home_grid_view?.apply {
            adapter = gridViewAdapter
        }
    }


}

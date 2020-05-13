package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthe100.arshop.R
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
import com.arthe100.arshop.views.adapters.OnItemClickListener
import com.arthe100.arshop.views.adapters.DiscountAdapter
import com.arthe100.arshop.views.adapters.HomeGridViewAdapter
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

    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messageManager = MessageManager()
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(ProductViewModel::class.java)
        cartViewModel = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(CartViewModel::class.java)
        dialogBox = DialogBoxManager()
        model.currentViewState.observe(this , Observer(::render))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        return inflater.inflate(R.layout.home_fragment_layout, container, false)
    }

    override fun onStart() {

        swipe_refresh_layout.setOnRefreshListener {
            model.onEvent(ProductUiAction.GetHomePageProducts)
            cartViewModel.onEvent(CartUiAction.GetCartOnStart)
            swipe_refresh_layout.isRefreshing = false
        }

        model.onEvent(ProductUiAction.GetHomePageProducts)
        cartViewModel.onEvent(CartUiAction.GetCartOnStart)
        dialogBox.cancel()
        super.onStart()
    }

    override fun toString(): String {
        return "Home"
    }

    private fun render(state: ProductState){
        when(state){
            is ProductState.Idle -> {
                dialogBox.cancel()
                home_fragment_layout.visibility = View.VISIBLE
            }

            is ProductState.LoadingState -> {
                home_fragment_layout.visibility = View.INVISIBLE
                dialogBox.showDialog(requireActivity(), MessageType.LOAD)
            }

            is ProductState.GetProductsSuccess -> {
                dialogBox.cancel()
                home_fragment_layout.visibility = View.VISIBLE
                setRecyclerView()
                setGridView()
                addProducts(state.products)
            }
            is ProductState.ProductDetailSuccess -> {
                dialogBox.cancel()
                home_fragment_layout.visibility = View.VISIBLE
                loadFragment(productFragment)
            }

            is ProductState.GetProductsFailure -> {
                home_fragment_layout.visibility = View.VISIBLE
                dialogBox.showDialog(requireContext(), MessageType.ERROR, "خطا در برقراری ارتباط با سرور")
            }
        }

    }

    private fun addProducts(data: List<Product>) {
        gridViewAdapter.submitList(data)
    }

//    private fun addDiscounts(discounts: List<String>) {
//        discountAdapter.submitList(discounts)
//    }

    private fun setRecyclerView() {
        discountAdapter = DiscountAdapter()
        discountAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(requireContext(),"Clicked", Toast.LENGTH_LONG).show()
            }
        })

        discount_recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            adapter = discountAdapter
        }
    }

    private fun setGridView() {
        gridViewAdapter = HomeGridViewAdapter(requireContext())

        home_grid_view.setOnItemClickListener { _, _, pos, _ ->
            model.onEvent(ProductUiAction.GetProductDetails(gridViewAdapter.getItem(pos)))
        }

        home_grid_view.apply {
            adapter = gridViewAdapter
        }
    }


}

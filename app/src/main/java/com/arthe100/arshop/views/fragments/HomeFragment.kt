package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthe100.arshop.R
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.Products.ProductState
import com.arthe100.arshop.scripts.mvi.Products.ProductUiAction
import com.arthe100.arshop.scripts.mvi.Products.ProductViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.ILoadFragment
import com.arthe100.arshop.views.adapters.ProductAdapter.ProductAdapter
import com.arthe100.arshop.views.data.DataSource
import com.arthe100.arshop.views.data.Product
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import javax.inject.Inject

class HomeFragment: BaseFragment(), ILoadFragment {

    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject lateinit var customArFragment: CustomArFragment
    @Inject lateinit var session: UserSession
    @Inject lateinit var messageManager: MessageManager

    private lateinit var productAdapter: ProductAdapter
    private lateinit var model: ProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(activity!! , viewModelProviderFactory).get(ProductViewModel::class.java)

        model.currentViewState.observe(this , Observer(::render))
    }

    private fun render(state: ProductState){
        when(state){
            is ProductState.Idle -> {
                loading_bar.visibility = View.INVISIBLE
            }
            is ProductState.LoadingState -> {
                loading_bar.visibility = View.VISIBLE

            }
            is ProductState.GetProductsSuccess -> {
                loading_bar.visibility = View.INVISIBLE
                setRecyclerView()
                addProducts(state.products)
            }

            is ProductState.GetProductsFaliure -> {
                loading_bar.visibility = View.INVISIBLE
                messageManager.toast(context!! , state.throwable.toString())
            }
        }
    }

    override fun inject() {
        (activity!!.application as BaseApplication).mainComponent(activity!!)
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity!!.bottom_navbar.visibility = View.VISIBLE
        return inflater.inflate(R.layout.home_fragment_layout, container, false)
    }

    override fun onStart() {

        when(session.user){
            is User.GuestUser ->{
                messageManager.toast(context!! , "not logged in!")
            }
            is User.User ->{
                model.onEvent(ProductUiAction.GetHomePageProducts)
            }
        }

        super.onStart()
    }

    private fun addProducts(products: List<com.arthe100.arshop.models.Product>) {
        productAdapter.submitList(products)
    }

    private fun setRecyclerView() {
        productAdapter = ProductAdapter()
        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }


    override fun loadFragment(fragment: Fragment?) {
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment!!, fragment.toString())
                .addToBackStack(fragment.tag)
                .commit()
    }

    override fun toString(): String {
        return "Home"
    }
}

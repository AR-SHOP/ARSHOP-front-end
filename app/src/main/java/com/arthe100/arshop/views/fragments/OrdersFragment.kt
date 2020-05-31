package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthe100.arshop.R
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.mvi.Auth.AuthState
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.views.BaseFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.orders_fragment.*
import javax.inject.Inject

class OrdersFragment @Inject constructor(
    private val session: UserSession
) : BaseFragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        requireActivity().bottom_navbar.visibility = View.VISIBLE
        return inflater.inflate(R.layout.orders_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()

        when(session.user){
            is User.User -> {
                login_btn.visibility = View.INVISIBLE
                empty_orders_layout.visibility = View.VISIBLE
                ordered_items_list.visibility = View.VISIBLE
            }
            else -> {
                login_btn.visibility = View.VISIBLE
                empty_orders_layout.visibility = View.INVISIBLE
                ordered_items_list.visibility = View.INVISIBLE
                login_btn.setOnClickListener {
                    requireActivity().bottom_navbar.visibility = View.INVISIBLE
                    loadFragment(LoginFragment::class.java)
                }
            }
        }
    }


    override fun toString(): String {
        return "Orders"
    }

    private fun authRender(state: AuthState) {
        when (state) {
            is AuthState.LoginSuccess -> {
                bottom_buttons.visibility = View.VISIBLE
                login_btn.visibility = View.INVISIBLE
                empty_orders_layout.visibility = View.VISIBLE
                ordered_items_list.visibility = View.INVISIBLE
            }
        }
    }

//
//    private fun setRecyclerView() {
////        cartItemAdapter = CartItemAdapter()
//        cartItemAdapter.setOnItemClickListener(object :
//            OnItemClickListener {
//            override fun onItemClick(position: Int) {
////                productFragment.setProduct(cartItemAdapter.dataList[position])
////                loadFragment(productFragment)
//            }
//        })
//        ordered_items_list.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = cartItemAdapter
//        }
//    }



}

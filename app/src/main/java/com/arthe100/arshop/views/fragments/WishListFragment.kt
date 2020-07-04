package com.arthe100.arshop.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.WishList.WishListViewModel
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.scripts.mvi.base.WishListState
import com.arthe100.arshop.scripts.mvi.base.WishListUiAction
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.base.GenericAdapter
import com.arthe100.arshop.views.adapters.base.GenericItemDiff
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import com.arthe100.arshop.views.interfaces.ILoadFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.fragment_wish_list.*
import javax.inject.Inject

class WishListFragment @Inject constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory
    ) : BaseFragment(), ILoadFragment {

    private lateinit var messageManager: MessageManager
    private lateinit var dialogBox: DialogBoxManager
    private lateinit var model: WishListViewModel
    private lateinit var wishListAdapter: GenericAdapter<Product>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        dialogBox = DialogBoxManager()
        messageManager = MessageManager()
        requireActivity().bottom_navbar?.visibility = View.INVISIBLE
        model = ViewModelProvider(requireActivity() , viewModelProviderFactory).get(WishListViewModel::class.java)
        return inflater.inflate(R.layout.fragment_wish_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentViewState.observe(viewLifecycleOwner , Observer(::render))
    }

    override fun onStart() {
        super.onStart()
        setRecyclerView()

        model.onEvent(WishListUiAction.GetWishListAction)
    }

    override fun render(state: ViewState) {
        when(state) {
            is ViewState.IdleState -> {
                dialogBox.cancel()
            }

            is ViewState.Failure -> {
                dialogBox.showDialog(requireContext(), MessageType.ERROR, state.throwable.toString())
                messageManager.toast(requireContext(), state.throwable.toString())
            }

            is WishListState.GetWishListSuccess -> {
                dialogBox.cancel()

                val wishList = state.getWishList
                model.currentWishList = wishList

                if (wishList.wishListItems.isNotEmpty()) {
                    empty_wishList_layout.visibility = View.INVISIBLE
                    wishListAdapter.addItems(wishList.wishListItems)
                }
            }

            is ViewState.LoadingState -> {
                dialogBox.showDialog(requireActivity(), MessageType.LOAD)
            }
        }
    }

    private fun setAdapter() {
        wishListAdapter = object : GenericAdapter<Product>() {
            override fun getLayoutId(position: Int, obj: Product): Int = R.layout.item_wish_list
        }

        wishListAdapter.apply {
            setDiffUtil(object : GenericItemDiff<Product> {
                override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                    return oldItem == newItem
                }
            })
        }
    }

    private fun setRecyclerView() {
        if (!this::wishListAdapter.isInitialized) setAdapter()
        wish_list_recycler_view?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = wishListAdapter
        }
    }

}

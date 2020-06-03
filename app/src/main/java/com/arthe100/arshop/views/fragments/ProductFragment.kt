package com.arthe100.arshop.views.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.Calendar
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arthe100.arshop.R
import com.arthe100.arshop.models.Comment
import com.arthe100.arshop.models.User
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.scripts.mvi.Auth.UserSession
import com.arthe100.arshop.scripts.mvi.Products.ProductViewModel
import com.arthe100.arshop.scripts.mvi.ar.ArViewModel
import com.arthe100.arshop.scripts.mvi.base.*
import com.arthe100.arshop.scripts.mvi.cart.CartViewModel
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.adapters.base.GenericAdapter
import com.arthe100.arshop.views.adapters.base.GenericItemDiff
import com.arthe100.arshop.views.adapters.base.OnItemClickListener
import com.arthe100.arshop.views.dialogBox.CommentDialog
import com.arthe100.arshop.views.dialogBox.DialogBoxManager
import com.arthe100.arshop.views.dialogBox.MessageType
import com.arthe100.arshop.views.utility.ShamsiCalendar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.ar.sceneform.ux.ArFragment
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.dialog_comment_layout.*
import kotlinx.android.synthetic.main.product_fragment_layout.*
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.util.*
import javax.inject.Inject


class ProductFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val messageManager: MessageManager,
    private val dialogBox: DialogBoxManager,
    private val session: UserSession
): BaseFragment() {

    private lateinit var commentRVAdapter: GenericAdapter<Comment>
    private lateinit var cartViewModel: CartViewModel
    private lateinit var model: ProductViewModel
    private lateinit var arModel: ArViewModel
    private lateinit var commentDialog: CommentDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        requireActivity().bottom_navbar.visibility = View.INVISIBLE
        model = ViewModelProvider(requireActivity() , viewModelFactory).get(ProductViewModel::class.java)
        arModel = ViewModelProvider(requireActivity() , viewModelFactory).get(ArViewModel::class.java)
        cartViewModel = ViewModelProvider(requireActivity() , viewModelFactory).get(CartViewModel::class.java)
        return inflater.inflate(R.layout.product_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentViewState.observe(viewLifecycleOwner , Observer(::render))
        cartViewModel.currentViewState.observe(viewLifecycleOwner , Observer(::render))
    }

    override fun onStart() {
        super.onStart()
        commentDialog = setCommentDialog()
        setComments(model.product.comments)
        (requireActivity() as AppCompatActivity).setSupportActionBar(product_toolbar)
        product_toolbar?.title = model.product.name
        product_details_name?.text = model.product.name
        product_details_brand?.text = model.product.manufacturer
        product_details_price?.text = model.product.price.toString()
        product_details_description?.text = model.product.description
        inc_dec_cart_count?.setBackgroundColor(Color.TRANSPARENT)
        val commentSize = model.product.comments.size
        comments_count?.text = if(commentSize == 0) "هیچ دیدگاهی ثبت نشده" else "$commentSize دیدگاه"
        checkCartStatus()

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.empty_background)
            .error(R.drawable.empty_background)

        val cartItem = cartViewModel.getCartItemById(model.product.id)
        cart_count_text?.text = cartItem?.quantity.toString()

        Glide.with(requireContext())
            .applyDefaultRequestOptions(requestOptions)
            .load(model.product.thumbnail)
            .into(product_details_image)
        if(model.product.arModel.isEmpty() || model.product.arModel.isBlank())
            ar_btn?.visibility = View.INVISIBLE
        ar_btn?.setOnClickListener {
            arModel.currentUri = model.product.arModel
            loadFragment(ArFragment::class.java)
        }
        add_to_cart_btn?.setOnClickListener {
            cartViewModel.onEvent(CartUiAction.AddToCart(model.product.id , 1))
            cart_count_text?.text = 1.toString()
            add_to_cart_btn?.visibility = View.INVISIBLE
            inc_dec_cart_count?.visibility = View.VISIBLE
        }

        when(session.user){
            is User.GuestUser ->
                add_to_cart_btn?.visibility = View.INVISIBLE
        }

        product_comment_confirm_btn?.setOnClickListener {
            showCommentDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.product_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_to_wish_list -> {
                //use this line of code to change the color of the heart icon
                //item.icon.setTint(Color.RED)
            }
        }
        return true
    }

    override fun toString(): String {
        return "Product"
    }

    private fun setCommentDialog() : CommentDialog {
        return CommentDialog(requireContext() , model)
    }

    private fun showCommentDialog() {
        if (!this::commentDialog.isInitialized)
            commentDialog = setCommentDialog()
        commentDialog.open()

//        } else {
//            commentDialog = setCommentDialog()
//            commentDialog.show()
//        }


//        comment = Comment(commentTitle, commentText)
    }


    override fun render(state: ViewState){
        when(state){
            ViewState.IdleState ->  dialogBox.cancel()
            ViewState.LoadingState -> dialogBox.showDialog(requireActivity(), MessageType.LOAD)
            is ProductState.ProductDetailSuccess -> {
                model.product = state.product
                commentDialog = setCommentDialog()
                commentRVAdapter.addItems(model.product.comments)
                (requireActivity() as AppCompatActivity).setSupportActionBar(product_toolbar)
                product_toolbar?.title = model.product.name
                product_details_name?.text = model.product.name
                product_details_brand?.text = model.product.manufacturer
                product_details_price?.text = model.product.price.toString()
                product_details_description?.text = model.product.description
                inc_dec_cart_count?.setBackgroundColor(Color.TRANSPARENT)
                val commentSize = model.product.comments.size
                comments_count?.text = if(commentSize == 0) "هیچ دیدگاهی ثبت نشده" else "$commentSize دیدگاه"
                dialogBox.cancel()
            }
            is ViewState.Failure -> dialogBox.showDialog(requireContext(), MessageType.ERROR, "خطا در برقراری ارتباط با سرور")
            is CartState.AddToCartState -> {
                dialogBox.cancel()
                checkCartStatus()
            }
            is ProductState.CommentSent -> {
                model.onEvent(ProductUiAction.GetProductDetails(model.product))
                commentDialog.close()
            }

        }
    }
    private fun checkCartStatus(){

        plus_btn?.setOnClickListener{
            val cartItem = cartViewModel.getCartItemById(model.product.id) ?: return@setOnClickListener
            val newQuantity = (cart_count_text?.text.toString().toInt() + 1).coerceIn(0..Int.MAX_VALUE)
            cartItem.quantity = newQuantity
            cart_count_text?.text = newQuantity.toString()
            cartViewModel.onEvent(CartUiAction.IncreaseQuantity(cartItem.product.id, cartItem.quantity))
        }
        minus_btn?.setOnClickListener{
            val cartItem = cartViewModel.getCartItemById(model.product.id) ?: return@setOnClickListener
            val newQuantity = (cart_count_text?.text.toString().toInt() - 1).coerceIn(0..Int.MAX_VALUE)
            cartItem.quantity = newQuantity
            if(newQuantity == 0)
                showAddToCartButton()
            cart_count_text?.text = newQuantity.toString()
            cartViewModel.onEvent(CartUiAction.DecreaseQuantity(cartItem.product.id , cartItem.quantity))
        }
        delete_btn?.setOnClickListener{
            val cartItem = cartViewModel.getCartItemById(model.product.id) ?: return@setOnClickListener
            cartItem.quantity = 0
            cartViewModel.onEvent(CartUiAction.RemoveFromCart(cartItem.product.id))
           showAddToCartButton()
        }

        if(cartViewModel.isInCart(model.product.id))
            showCartButtons()
        else
            showAddToCartButton()

    }

    private fun setComments(comments: List<Comment>){
        if(this::commentRVAdapter.isInitialized)
        {
            user_comments_recycler_view?.apply {
                layoutManager = LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
                adapter = commentRVAdapter
            }
            commentRVAdapter.addItems(comments)
        }

        commentRVAdapter = object: GenericAdapter<Comment>(){
            override fun getLayoutId(position: Int, obj: Comment): Int = R.layout.user_comment_card_item
        }
        commentRVAdapter.apply {
            setDiffUtil(object: GenericItemDiff<Comment>{
                override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                    oldItem.content == newItem.content &&
                    oldItem.rating == newItem.rating &&
                    oldItem.timestamp == newItem.timestamp
            })
        }

        user_comments_recycler_view?.apply {
            layoutManager = LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
            adapter = commentRVAdapter
        }
        commentRVAdapter.addItems(comments)
    }

    private fun showAddToCartButton(){
        add_to_cart_btn?.visibility = View.VISIBLE
        inc_dec_cart_count?.visibility = View.INVISIBLE
    }

    private fun showCartButtons(){
        add_to_cart_btn?.visibility = View.INVISIBLE
        inc_dec_cart_count?.visibility = View.VISIBLE
    }

}

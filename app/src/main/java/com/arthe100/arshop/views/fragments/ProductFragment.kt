package com.arthe100.arshop.views.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arthe100.arshop.R
import com.arthe100.arshop.models.Product
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.views.BaseFragment
import com.arthe100.arshop.views.ILoadFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.product_fragment_layout.*
import java.net.URI
import javax.inject.Inject

class ProductFragment : BaseFragment(), ILoadFragment {

    @Inject lateinit var customArFragment: CustomArFragment
    private lateinit var product: Product


    override fun inject() {
        (requireActivity().application as BaseApplication).mainComponent(requireActivity())
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().bottom_navbar.visibility = View.INVISIBLE
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.product_fragment_layout, container, false)
    }


    override fun onStart() {
        name_text.text = product.name
        brand_text.text = product.manufacturer
        description_text.text = product.description

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(requireContext())
            .applyDefaultRequestOptions(requestOptions)
            .load(product.thumbnail)
            .into(product_image)

        ar_btn.setOnClickListener {
            customArFragment.setUri(product.arModel)
            loadFragment(customArFragment)
        }

        super.onStart()
    }

    fun setProduct(product: Product) {
        this.product = product
    }

    override fun loadFragment(fragment: Fragment?) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment!!, fragment.toString())
            .addToBackStack(fragment.tag)
            .commit()
    }

}

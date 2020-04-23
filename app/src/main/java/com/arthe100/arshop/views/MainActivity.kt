package com.arthe100.arshop.views

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.arthe100.arshop.R
import com.arthe100.arshop.scripts.di.BaseApplication
import com.arthe100.arshop.scripts.messege.MessageManager
import com.arthe100.arshop.views.adapters.BottomNavigationViewAdapter
import com.arthe100.arshop.views.adapters.SearchViewAdapter
import com.arthe100.arshop.views.atoms.ButtonAV
import com.arthe100.arshop.views.fragments.CustomArFragment
import com.arthe100.arshop.views.fragments.LoadingFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.victor.loading.newton.NewtonCradleLoading
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.loading_layout.*
import javax.inject.Inject


class MainActivity : BaseActivity(), ILoadFragment {


    private val TAG : String? = MainActivity::class.simpleName

    @Inject lateinit var messageManager: MessageManager
    @Inject lateinit var customArFragment: CustomArFragment

    override fun inject() {
        (application as BaseApplication)
                .appComponent
                .mainComponent()
                .create(this) // add this context to the container
                .inject(this) // i want to get injected
    }

    private lateinit var searchView: MaterialSearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)






        BottomNavigationViewAdapter(this, savedInstanceState)
                .setBottomNavigationView()



//        button.setOnClickListener {
//            MaterialDialog(this)
//                        title = "Error"
//                        message(R.string.username_hint)
//                        positiveButton(R.string.ok)
//                    }
//        }



        initializeToolBar()
        searchView = SearchViewAdapter(this)
                .setSearchView().searchView

    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_layout, menu)
        val item: MenuItem = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initializeToolBar() {
        var toolbar = tool_bar
        setSupportActionBar(toolbar)
        toolbar_container.visibility = View.VISIBLE
    }

    override fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_right)
                .add(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
    }
}

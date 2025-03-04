package com.polsl.yerbapp

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.andrognito.flashbar.Flashbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.navHostMain)
        bottomNav.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(navigationChangedListener)
        setSupportActionBar(appBar)
    }

    private val navigationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, arguments ->
            when (destination.id) {
                R.id.registerFragment -> {
                    bottomNav.visibility = View.GONE
                    setAppbar(getString(R.string.REGISTRATION), true)
                }
                R.id.productPreviewFragment -> {
                    bottomNav.visibility = View.GONE
                    setAppbar(getString(R.string.PRODUCT_DETAILS), true)
                }
                R.id.addProductFragment -> {
                    bottomNav.visibility = View.GONE
                    setAppbar(getString(R.string.ADD_PRODUCT), true)

                }
                else -> {
                    bottomNav.visibility = View.VISIBLE
                    setAppbar()
                }
            }

        }

    private fun setAppbar(title: String = "", isBackEnabled: Boolean = false) {
        supportActionBar?.setDisplayHomeAsUpEnabled(isBackEnabled)

        appBarTitle.text = if (title.isEmpty()) getString(R.string.app_name) else title
        appBarLogo.visibility = if (title.isEmpty() && !isBackEnabled) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    fun showSnackbar(@StringRes stringRes: Int) {
        Flashbar.Builder(this)
            .gravity(Flashbar.Gravity.TOP)
            .message(stringRes)
//            .messageTypeface(Typeface.DEFAULT_BOLD)
            .messageSizeInSp(18f)
            .dismissOnTapOutside()
            .duration(Flashbar.DURATION_LONG)
            .backgroundColorRes(R.color.colorPrimaryDark)
            .build()
            .show()
    }


}

package com.polsl.yerbapp

import android.os.Bundle
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
    }

    fun showSnackbar(@StringRes stringRes: Int) {
        Flashbar.Builder(this)
            .gravity(Flashbar.Gravity.TOP)
            .message(stringRes)
            .dismissOnTapOutside()
            .duration(Flashbar.DURATION_LONG)
            .backgroundColorRes(R.color.colorPrimaryDark)
            .build()
            .show()
    }
}

package com.polsl.yerbapp.presentation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.polsl.yerbapp.MainActivity

open class BaseFragment<T : BaseViewModel> : Fragment() {
    open val viewModel: T? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupLiveData()
        super.onViewCreated(view, savedInstanceState)
    }

    open fun setupLiveData() {
        viewModel?.navigationProps?.observe(viewLifecycleOwner, Observer {
            navigate(it.navigationId, it.bundle)
        })
        viewModel?.message?.observe(viewLifecycleOwner, Observer {
            showMessage(it)
        })
    }

    fun navigate(@IdRes actionId: Int, bundle: Bundle?) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }

    fun navigate(navDirection: NavDirections) {
        view?.let { Navigation.findNavController(it).navigate(navDirection) }
    }

    fun showMessage(@StringRes stringRes: Int) {
        (activity as? MainActivity)?.showSnackbar(stringRes)
    }
}

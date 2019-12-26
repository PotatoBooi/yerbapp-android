package com.polsl.yerbapp.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.polsl.yerbapp.R
import com.polsl.yerbapp.databinding.ProfileFragmentBinding
import com.polsl.yerbapp.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.profile_fragment.*
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProfileFragment : BaseFragment<ProfileViewModel>(), AuthListener {
    override val viewModel: ProfileViewModel? by viewModel { parametersOf(this) }
    private lateinit var binding: ProfileFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        binding.viewmodel = viewModel

        return binding.root
    }

    override fun checkUserStatus() {
        viewModel?.checkAuthStatus()
    }

    override fun setupLiveData() {
        super.setupLiveData()

        viewModel?.authStatus?.observe(viewLifecycleOwner, Observer {
            when (it) {
                AuthStatus.LOADING -> {
                    clProfile.visibility = View.GONE
                    clLogin.visibility = View.GONE
                    clLoading.visibility =  View.VISIBLE
                }
                AuthStatus.AUTHORIZED -> {
                    clProfile.visibility = View.VISIBLE
                    clLogin.visibility = View.GONE
                    clLoading.visibility =  View.GONE
                }
                AuthStatus.UNAUTHORIZED -> {
                    clProfile.visibility = View.GONE
                    clLogin.visibility = View.VISIBLE
                    clLoading.visibility =  View.GONE
                }
            }
        })

    }
}

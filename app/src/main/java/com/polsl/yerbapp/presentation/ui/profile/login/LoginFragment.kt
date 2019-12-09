package com.polsl.yerbapp.presentation.ui.profile.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.polsl.yerbapp.R
import com.polsl.yerbapp.databinding.LoginFragmentBinding
import com.polsl.yerbapp.presentation.base.BaseFragment
import com.polsl.yerbapp.presentation.ui.profile.AuthListener
import com.polsl.yerbapp.presentation.ui.profile.ProfileSharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LoginFragment : BaseFragment<LoginViewModel>() {
    override val viewModel: LoginViewModel? by viewModel { parametersOf(this) }
    private val profileSharedViewModel: ProfileSharedViewModel? by sharedViewModel {
        parametersOf(
            this
        )
    }
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun setupLiveData() {
        super.setupLiveData()
        viewModel?.notifyUserLoggedIn?.observe(viewLifecycleOwner, Observer {
            (requireParentFragment() as? AuthListener)?.checkUserStatus()
        })
        profileSharedViewModel?.registerSuccess?.observe(viewLifecycleOwner, Observer {
            viewModel?.onRegisterSuccess(it)
        })
    }
}

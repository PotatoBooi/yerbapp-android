package com.polsl.yerbapp.presentation.ui.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.polsl.yerbapp.R
import com.polsl.yerbapp.databinding.RegisterFragmentBinding
import com.polsl.yerbapp.presentation.base.BaseFragment
import com.polsl.yerbapp.presentation.ui.profile.ProfileSharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RegisterFragment : BaseFragment<RegisterViewModel>() {
    override val viewModel: RegisterViewModel? by viewModel { parametersOf(this) }
    private val profileSharedViewModel: ProfileSharedViewModel? by sharedViewModel {
        parametersOf(
            this
        )
    }
    private lateinit var binding: RegisterFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.register_fragment, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun setupLiveData() {
        super.setupLiveData()
        viewModel?.notifyRegisterSuccess?.observe(viewLifecycleOwner, Observer {
            profileSharedViewModel?.handleRegisterSuccess(it)
            Navigation.findNavController(requireView()).popBackStack()
        })
    }
}

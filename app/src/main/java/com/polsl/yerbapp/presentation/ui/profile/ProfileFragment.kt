package com.polsl.yerbapp.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.polsl.yerbapp.R
import com.polsl.yerbapp.databinding.ProfileFragmentBinding
import com.polsl.yerbapp.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProfileFragment : BaseFragment<ProfileViewModel>() {
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


}

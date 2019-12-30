package com.polsl.yerbapp.presentation.ui.training.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.polsl.yerbapp.R
import com.polsl.yerbapp.databinding.TutorialFragmentBinding
import com.polsl.yerbapp.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TutorialFragment : BaseFragment<TutorialViewModel>() {
    override val viewModel: TutorialViewModel? by viewModel { parametersOf(this) }

    private lateinit var binding: TutorialFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.tutorial_fragment, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }

}

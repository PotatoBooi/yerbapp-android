package com.polsl.yerbapp.presentation.ui.explore.product_preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.polsl.yerbapp.R
import com.polsl.yerbapp.databinding.ProductPreviewFragmentBinding
import com.polsl.yerbapp.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductPreviewFragment : BaseFragment<ProductPreviewViewModel>() {
    override val viewModel: ProductPreviewViewModel? by viewModel { parametersOf(this) }
    private lateinit var binding: ProductPreviewFragmentBinding

    companion object {
        fun newInstance() = ProductPreviewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.product_preview_fragment, container, false)
        binding.viewmodel = viewModel

        return binding.root
    }

    override fun setupLiveData() {
        super.setupLiveData()
    }
}

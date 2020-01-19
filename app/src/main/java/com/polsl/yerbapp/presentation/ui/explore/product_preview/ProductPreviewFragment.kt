package com.polsl.yerbapp.presentation.ui.explore.product_preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.polsl.yerbapp.R
import com.polsl.yerbapp.databinding.ProductPreviewFragmentBinding
import com.polsl.yerbapp.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductPreviewFragment : BaseFragment<ProductPreviewViewModel>() {
    private val args: ProductPreviewFragmentArgs by navArgs()
    override val viewModel: ProductPreviewViewModel? by viewModel { parametersOf(args.productId) }
    private lateinit var binding: ProductPreviewFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.product_preview_fragment, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }
}

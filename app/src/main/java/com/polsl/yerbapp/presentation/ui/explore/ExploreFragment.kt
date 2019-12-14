package com.polsl.yerbapp.presentation.ui.explore

import ProductsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.polsl.yerbapp.R
import com.polsl.yerbapp.databinding.ExploreFragmentBinding
import com.polsl.yerbapp.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ExploreFragment : BaseFragment<ExploreViewModel>() {
    override val viewModel: ExploreViewModel? by viewModel { parametersOf(this) }
    private lateinit var binding: ExploreFragmentBinding

    companion object {
        fun newInstance() = ExploreFragment()
    }


   // private lateinit var viewModel: ExploreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.explore_fragment, container, false)
        binding.viewmodel = viewModel

        val adapter = ProductsAdapter()
        binding.productList.adapter = adapter

        viewModel?.getProducts()?.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })


        val manager = GridLayoutManager(activity, 3)
        binding.productList.layoutManager = manager

        return binding.root
    }

}

package com.polsl.yerbapp.presentation.ui.explore


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.polsl.yerbapp.R
import com.polsl.yerbapp.databinding.ExploreFragmentBinding
import com.polsl.yerbapp.presentation.base.BaseFragment
import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsAdapter
import kotlinx.android.synthetic.main.product_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ExploreFragment : BaseFragment<ExploreViewModel>() {
    override val viewModel: ExploreViewModel? by viewModel { parametersOf(this) }
    private lateinit var binding: ExploreFragmentBinding
    private lateinit var adapter: ProductsAdapter
    companion object {
        fun newInstance() = ExploreFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.explore_fragment, container, false)
        binding.viewmodel = viewModel
        adapter = ProductsAdapter(binding.viewmodel)
        binding.productList.adapter = adapter

        val manager = GridLayoutManager(activity, 2)
        binding.productList.layoutManager = manager
        return binding.root
    }

    override fun setupLiveData() {
        super.setupLiveData()
        viewModel?.pagedProducts?.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel?.loading?.observe(viewLifecycleOwner, Observer {
            binding.loading = it })
//                clLoading.visibility = View.VISIBLE
//            }
//            else
//            {
//                clLoading.visibility = View.INVISIBLE
//            }
//        })
//    }

    }

}

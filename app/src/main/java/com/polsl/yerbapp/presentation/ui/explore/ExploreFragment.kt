package com.polsl.yerbapp.presentation.ui.explore


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mancj.materialsearchbar.MaterialSearchBar
import com.polsl.yerbapp.R
import com.polsl.yerbapp.databinding.ExploreFragmentBinding
import com.polsl.yerbapp.presentation.base.BaseFragment
import com.polsl.yerbapp.presentation.ui.explore.adapters.ProductsAdapter
import kotlinx.android.synthetic.main.explore_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ExploreFragment : BaseFragment<ExploreViewModel>(), MaterialSearchBar.OnSearchActionListener {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchBar.setOnSearchActionListener(this)
    }

    override fun setupLiveData() {
        super.setupLiveData()
        viewModel?.pagedProducts?.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel?.loading?.observe(viewLifecycleOwner, Observer {
            binding.loading = it
        })
    }

    override fun onButtonClicked(buttonCode: Int) {
    }

    override fun onSearchStateChanged(enabled: Boolean) {
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        viewModel?.search(text.toString())
    }

}





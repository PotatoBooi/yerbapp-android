package com.polsl.yerbapp.presentation.ui.explore.add_product

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.polsl.yerbapp.R
import com.polsl.yerbapp.databinding.AddProductFragmentBinding
import com.polsl.yerbapp.domain.models.reponse.graphql.ProductModel
import com.polsl.yerbapp.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.add_product_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AddProductFragment : BaseFragment<AddProductViewModel>() {
    override val viewModel: AddProductViewModel? by viewModel { parametersOf(this) }
    private lateinit var binding: AddProductFragmentBinding

    companion object {
        fun newInstance() = AddProductFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_product_fragment, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun setupLiveData() {
        super.setupLiveData()

//        viewModel?.types?.observe( this, Observer {array ->
//            activity?.baseContext?.let{ ctx ->
//                val typeSpinnerAdapter =
//                    ArrayAdapter(ctx, R.layout.dropdown_item, array)
//                sType.adapter = typeSpinnerAdapter
//            }
//        })
    }
}

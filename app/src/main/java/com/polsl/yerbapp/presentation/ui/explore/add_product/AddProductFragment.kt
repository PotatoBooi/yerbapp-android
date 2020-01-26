package com.polsl.yerbapp.presentation.ui.explore.add_product

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.polsl.yerbapp.R
import com.polsl.yerbapp.databinding.AddProductFragmentBinding
import com.polsl.yerbapp.presentation.base.BaseFragment
import com.polsl.yerbapp.presentation.ui.explore.ProductsSharedViewModel
import kotlinx.android.synthetic.main.add_product_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class AddProductFragment : BaseFragment<AddProductViewModel>() {
    override val viewModel: AddProductViewModel? by viewModel { parametersOf(this) }
    private lateinit var binding: AddProductFragmentBinding
    private lateinit var imageView: ImageView
    private val productsSharedViewModel: ProductsSharedViewModel by sharedViewModel {
        parametersOf(
            this
        )
    }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sType.item = viewModel!!.types
        sType.setSearchDialogGravity(Gravity.CENTER)
        sType.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                viewModel!!.typeInputIndex.set(position)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                //sType.errorText = "On Nothing Selected"
            }
        }

        sManufacturer.item = viewModel!!.manufacturers
        sManufacturer.setSearchDialogGravity(Gravity.CENTER)
        sManufacturer.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                viewModel!!.manufacturerInputIndex.set(position)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                //sType.errorText = "On Nothing Selected"
            }
        }
        imageView = ivPhoto
        ivPhoto.setOnClickListener {
            ImagePicker.create(this)
                .returnMode(ReturnMode.ALL)
                .folderMode(true)
                .single()
                .toolbarFolderTitle(context?.resources?.getString(R.string.GALLERY))
                .start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val images = ImagePicker.getImages(data)

        if (!images.isNullOrEmpty()) {
            viewModel?.productImagePath?.set(images[0].path)
            viewModel?.isImageSet?.set(true) // can one observable depend on another automatically?
        }
    }

    override fun setupLiveData() {
        super.setupLiveData()
        viewModel?.productAdded?.observe(viewLifecycleOwner, Observer {
            Navigation.findNavController(requireActivity(), R.id.navHostMain)
                .popBackStack(R.id.exploreFragment, false)
            productsSharedViewModel.notifyRefreshList()
        })
    }
}

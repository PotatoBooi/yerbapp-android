package com.polsl.yerbapp.presentation.ui.explore.add_product

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.polsl.yerbapp.R
import com.polsl.yerbapp.databinding.AddProductFragmentBinding
import com.polsl.yerbapp.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.add_product_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AddProductFragment : BaseFragment<AddProductViewModel>() {
    override val viewModel: AddProductViewModel? by viewModel { parametersOf(this) }
    private lateinit var binding: AddProductFragmentBinding
    private lateinit var imageView:ImageView

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

        imageView = ivPhoto
        btnAddPhoto.setOnClickListener {
            ImagePicker.create(this)
                .returnMode(ReturnMode.ALL)
                .folderMode(true)
                .single()
                .toolbarFolderTitle("Folder")
                .toolbarImageTitle("Dotknij, aby wybrać")
                .toolbarDoneButtonText("Zatwierdź")
                .start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            val images = ImagePicker.getImages(data)
            if(!images.isNullOrEmpty()){
                imageView.setImageBitmap(BitmapFactory.decodeFile(images[0].path))
            }

    }


//    override fun setupLiveData() {
//        super.setupLiveData()
//

//        viewModel?.types?.observe( this, Observer {array ->
//            activity?.baseContext?.let{ ctx ->
//                val typeSpinnerAdapter =
//                    ArrayAdapter(ctx, R.layout.dropdown_item, array)
//                sType.adapter = typeSpinnerAdapter
//            }
//        }) //   }

}

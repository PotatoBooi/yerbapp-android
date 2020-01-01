package com.polsl.yerbapp.presentation.ui.explore.add_product

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.jaiselrahman.hintspinner.HintSpinnerAdapter
import com.polsl.yerbapp.R
import com.polsl.yerbapp.databinding.AddProductFragmentBinding
import com.polsl.yerbapp.domain.models.reponse.graphql.ManufacturerModel
import com.polsl.yerbapp.domain.models.reponse.graphql.TypeModel
import com.polsl.yerbapp.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.add_product_fragment.*
import kotlinx.android.synthetic.main.product_item.*
import kotlinx.android.synthetic.main.product_item.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.atomic.AtomicIntegerArray


class AddProductFragment : BaseFragment<AddProductViewModel>() {
    override val viewModel: AddProductViewModel? by viewModel { parametersOf(this) }
    private lateinit var binding: AddProductFragmentBinding
    private lateinit var imageView:ImageView
    private var dpHeight = 0

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

        //sManufacturer.setItems(viewModel!!.manufacturers)
        sManufacturer.setAdapter(HintSpinnerAdapter<ManufacturerModel>(context, viewModel!!.manufacturers, context?.resources?.getString(R.string.MANUFACTURER)))
        sType.setAdapter(HintSpinnerAdapter<TypeModel>(context, viewModel!!.types, context?.resources?.getString(R.string.TYPE)))

        imageView = ivPhoto
        ivPhoto.setOnClickListener {
            ImagePicker.create(this)
                .returnMode(ReturnMode.ALL)
                .folderMode(true)
                .single()
                .toolbarFolderTitle(context?.resources?.getString(R.string.GALLERY))
                .start()
        }
        btnRemovePhoto.setOnClickListener{ onRemovePhotoClick() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            val images = ImagePicker.getImages(data)
            if(!images.isNullOrEmpty()){
                imageView.setImageBitmap(BitmapFactory.decodeFile(images[0].path))
                viewModel?.productImagePath = images[0].path
            }
        // TODO bind image here to bytearray?

    }

    private fun onRemovePhotoClick(){
        imageView.setImageDrawable(context?.resources?.getDrawable(R.drawable.ic_add_photo))
        viewModel?.productImagePath = ""
    }


}

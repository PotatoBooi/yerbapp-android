package com.polsl.yerbapp.presentation.ui.explore.add_product

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.viewModelScope
import com.polsl.yerbapp.R
import com.polsl.yerbapp.domain.exceptions.NoConnectivityException
import com.polsl.yerbapp.domain.exceptions.UnauthorizedException
import com.polsl.yerbapp.domain.models.reponse.graphql.ManufacturerModel
import com.polsl.yerbapp.domain.models.reponse.graphql.TypeModel
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.usecases.ProductCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddProductViewModel(private val productCase: ProductCase) : BaseViewModel() {
    val loading = ObservableBoolean(true)

    val name = ObservableField<String>("")
    val details = ObservableField<String>("")

    val types = ObservableArrayList<TypeModel>()
    val selectedTypeIndex = ObservableInt(0)

    val manufacturers = ObservableArrayList<ManufacturerModel>()
    val selectedManufacturerIndex = ObservableInt(0)

    init{
        initTypes()
        initManufacturers()
    }


    fun onSaveClick() {

        // TODO validation
        // TODO Post photo or default photo
        // TODO add product with url photo adress
    }

    private fun initTypes(){
        viewModelScope.launch(Dispatchers.Main) {
            try{
                loading.set(true)
                val typesData = productCase.getProductTypes()
                types.clear()
                types.addAll(typesData)
                loading.set(false)
                }catch (ex: Exception) {
                    loading.set(false)
                    handleErrors(ex)
                }
        }
    }

    private fun initManufacturers(){
        viewModelScope.launch(Dispatchers.Main) {
            try{
                loading.set(true)
                val manufacturersData = productCase.getProductManfacturers()
                manufacturers.clear()
                manufacturers.addAll(manufacturersData)
                loading.set(false)
            }catch (ex: Exception) {
                loading.set(false)
                handleErrors(ex)
            }
        }
    }

    private fun handleErrors(ex: Exception) {
        when (ex) {
            is UnauthorizedException -> {
                _message.postValue(R.string.UNAUTHORIZED)
                // TODO navigate to login? is it possible?
            }
            is NoConnectivityException -> {
                _message.postValue(R.string.NO_INTERNET)
            }
            else -> _message.postValue(R.string.BAD_RESPONSE)
        }
    }
}

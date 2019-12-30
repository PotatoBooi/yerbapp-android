package com.polsl.yerbapp.presentation.ui.explore.add_product

import android.media.Image
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
import com.polsl.yerbapp.domain.models.reponse.graphql.ProductModel
import com.polsl.yerbapp.domain.models.reponse.graphql.TypeModel
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.base.NavigationProps
import com.polsl.yerbapp.presentation.usecases.ProductCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddProductViewModel(private val productCase: ProductCase) : BaseViewModel() {
    val loading = ObservableBoolean(true)

    val nameInput = ObservableField<String>("")
    val detailsInput = ObservableField<String>("")

    val types = ObservableArrayList<TypeModel>()
    val typeInputIndex = ObservableInt(0)

    val manufacturers = ObservableArrayList<ManufacturerModel>()
    val manufacturerInputIndex = ObservableInt(0)
    var productImagePath: String = ""

    private val manufacturerId: String?
        get() = manufacturers[manufacturerInputIndex.get()].id
    private val typeId: String?
        get() = types[typeInputIndex.get()].id

    init{
        initTypes()
        initManufacturers()
    }


    fun onSaveClick() {
        when {
            nameInput.get()?.isEmpty() ?: true -> {
                _message.value = R.string.PRODUCT_NAME_EMPTY
                return
            }
        }
        viewModelScope.launch(Dispatchers.Main) {
            try {
                loading.set(true)
                val result = productCase.addProduct(
                    nameInput.get() ?: "",
                    detailsInput.get() ?: "",
                    typeId ?: "",
                    manufacturerId ?: "",
                    productImagePath ?: ""
                )
               // notify if saved -> navigate to explore
                if(!result.isNullOrEmpty()) {
                    _message.postValue(R.string.PRODUCT_ADDED)
                    val navigationId = R.id.action_addProductFragment_to_exploreFragment
                    _navigationProps.value = NavigationProps(navigationId, null)
                }
            } catch (ex: Exception) {
                handleErrors(ex)
            } finally {
                loading.set(false)
            }
        }
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
                val manufacturersData = productCase.getProductManufacturers()
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
                val navigationId = R.id.action_addProductFragment_to_profileFragment
                _navigationProps.value = NavigationProps(navigationId, null)
            }
            is NoConnectivityException -> {
                _message.postValue(R.string.NO_INTERNET)
            }
            else -> _message.postValue(R.string.BAD_RESPONSE)
        }
    }
}

package com.polsl.yerbapp.presentation.ui.explore.add_product

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
import com.polsl.yerbapp.presentation.base.NavigationProps
import com.polsl.yerbapp.presentation.usecases.ProductCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


class AddProductViewModel(private val productCase: ProductCase) : BaseViewModel() {
    val loading = ObservableBoolean(true)

    val nameInput = ObservableField<String>("")
    val detailsInput = ObservableField<String>("")

    val types = ObservableArrayList<TypeModel>()
    val typeInputIndex = ObservableInt(0)

    val manufacturers = ObservableArrayList<ManufacturerModel>()
    val manufacturerInputIndex = ObservableInt()

    val productImagePath = ObservableField<String>()
    val isImageSet = ObservableBoolean(false)

    private val manufacturerId: String?
        get() = manufacturers[manufacturerInputIndex.get() - 1].id
    private val typeId: String?
        get() = types[typeInputIndex.get() - 1].id

    init {
        initTypes()
        initManufacturers()
    }


    fun onRemovePhotoClick() {
        productImagePath?.set("")
        isImageSet?.set(false)
    }

    fun onSaveClick() {
        when {
            nameInput.get()?.isEmpty() ?: true -> {
                _message.value = R.string.PRODUCT_NAME_EMPTY
                return
            }
        }
        when {
            typeInputIndex.get() == 0 -> {
                _message.value = R.string.TYPE_EMPTY
                return
            }
        }
        when {
            manufacturerInputIndex.get() == 0 -> {
                _message.value = R.string.MANUFACTURER_EMPTY
                return
            }
        }
        viewModelScope.launch(Dispatchers.Main) {
            try {
                loading.set(true)
                val file = productImagePath.get()?.let {
                    if (it.isNotEmpty()) {
                        File(it)
                    } else {
                        null
                    }
                } ?: run { null }
                val result = productCase.addProduct(
                    nameInput.get() ?: "",
                    detailsInput.get() ?: "",
                    typeId ?: "",
                    manufacturerId ?: "",
                    file
                )
                if (!result.isNullOrEmpty()) {
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

    private fun initTypes() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                loading.set(true)
                val typesData = productCase.getProductTypes()
                types.clear()
                types.addAll(typesData)
                loading.set(false)
            } catch (ex: Exception) {
                loading.set(false)
                handleErrors(ex)
            }
        }
    }

    private fun initManufacturers() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                loading.set(true)
                val manufacturersData = productCase.getProductManufacturers()
                manufacturers.clear()
                manufacturers.addAll(manufacturersData)
                loading.set(false)
            } catch (ex: Exception) {
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

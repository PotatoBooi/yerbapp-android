package com.polsl.yerbapp.presentation.ui.explore.adapters

import androidx.lifecycle.LiveData
import androidx.paging.PageKeyedDataSource
import com.hadilq.liveevent.LiveEvent
import com.polsl.yerbapp.domain.models.reponse.graphql.ProductModel
import com.polsl.yerbapp.presentation.usecases.ProductsCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class ProductsDataSource(
    private val scope: CoroutineScope,
    private val productsCase: ProductsCase, private val filter: String?
) : PageKeyedDataSource<Int, ProductModel>() {

    val loading: LiveData<Boolean>
        get() = _loading
    private val _loading = LiveEvent<Boolean>()

    val error: LiveData<Boolean>
        get() = _error
    private val _error = LiveEvent<Boolean>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ProductModel>
    ) {
        scope.launch {
            _loading.postValue(true)
            try {
                val items = productsCase.getProducts(filter, params.requestedLoadSize, 0)
                callback.onResult(items, null, params.requestedLoadSize)
            } catch (ex: Exception) {
                _error.postValue(true)
            } finally {
                _loading.postValue(false)
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ProductModel>
    ) {
        scope.launch {
            _loading.postValue(true)
            try {
                val items = productsCase.getProducts(filter, params.requestedLoadSize, params.key)
                callback.onResult(items, params.key + params.requestedLoadSize)
            } catch (ex: Exception) {
                _error.postValue(true)
            } finally {
                _loading.postValue(false)
            }
        }

    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ProductModel>
    ) {
    }

//    private fun handleErrors(ex: Exception) {
//        when (ex) {
//            is NoConnectivityException -> {
//                //_errorMessage.postValue("Nie masz połączenia z internetem.\nSpróbuj ponownie później.")
//            }
//            else -> _errorMessage.postValue("Wystąpił błąd. Spróbuj ponownie później")
//        }
//    }

}
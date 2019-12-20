package com.polsl.yerbapp.presentation.ui.explore.adapters

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.polsl.yerbapp.data.ProductsRepository
import com.polsl.yerbapp.domain.models.ProductModel

class ProductsDataSourceFactory(private val productsRepository: ProductsRepository) : DataSource.Factory<Int?, ProductModel?>() {
    private val liveData: MutableLiveData<ProductsDataSource> = MutableLiveData()
    //private val repository: Repository
    //private val compositeDisposable: CompositeDisposable
    val productsLiveData: MutableLiveData<ProductsDataSource>
        get() = liveData


    override fun create(): DataSource<Int?, ProductModel?>? {
        val dataSourceClass =
            ProductsDataSource(productsRepository)
        liveData.postValue(dataSourceClass)
        return dataSourceClass
    }

}

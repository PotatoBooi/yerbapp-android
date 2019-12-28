package com.polsl.yerbapp.presentation.usecases

import com.polsl.yerbapp.data.ProductsRepository
import android.util.Log
import com.polsl.yerbapp.domain.models.reponse.graphql.ProductModel

class ProductsCase(private val productsRepository: ProductsRepository) {

    val ORDER_BY = "name"
    suspend fun getProducts(perPage: Int, offset: Int) = productsRepository.getProducts(perPage, offset, ORDER_BY)

}
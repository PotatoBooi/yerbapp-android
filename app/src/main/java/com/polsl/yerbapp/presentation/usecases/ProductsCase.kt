package com.polsl.yerbapp.presentation.usecases

import com.polsl.yerbapp.data.ProductsRepository
import android.util.Log

class ProductsCase(private val productsRepository: ProductsRepository) {

    val ORDER_BY = "name"
    suspend fun getProducts(perPage: Int, offset: Int) = productsRepository.getProducts(perPage, offset, ORDER_BY)
    suspend fun getProduct(productId: String) = productsRepository.getProduct(productId)

}
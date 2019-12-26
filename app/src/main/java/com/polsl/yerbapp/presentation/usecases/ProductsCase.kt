package com.polsl.yerbapp.presentation.usecases

import com.polsl.yerbapp.data.ProductsRepository
import android.util.Log

class ProductsCase(private val productsRepository: ProductsRepository) {

    suspend fun getProducts(perPage: Int, offset: Int, orderBy: String) = productsRepository.getProducts(perPage, offset, orderBy)
    suspend fun getProduct(productId: String) = productsRepository.getProduct(productId)

}
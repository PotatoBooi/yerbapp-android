package com.polsl.yerbapp.presentation.usecases

import com.polsl.yerbapp.data.ProductsRepository


class ProductCase(private val productsRepository: ProductsRepository) {

    val ORDER_BY = "name"


    suspend fun getProduct(productId: String) = productsRepository.getProduct(productId)

}
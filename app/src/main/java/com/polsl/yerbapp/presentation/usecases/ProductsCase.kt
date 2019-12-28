package com.polsl.yerbapp.presentation.usecases

import com.polsl.yerbapp.data.repos.ProductsRepository

class ProductsCase(private val productsRepository: ProductsRepository) {

    val ORDER_BY = "name"
    suspend fun getProducts(perPage: Int, offset: Int) = productsRepository.getProducts(perPage, offset, ORDER_BY)

}
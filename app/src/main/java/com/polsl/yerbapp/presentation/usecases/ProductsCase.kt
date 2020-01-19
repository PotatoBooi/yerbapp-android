package com.polsl.yerbapp.presentation.usecases

import com.polsl.yerbapp.data.repos.ProductsRepository
import com.polsl.yerbapp.domain.models.reponse.graphql.ProductModel

class ProductsCase(private val productsRepository: ProductsRepository) {

    val ORDER_BY = "name"
    suspend fun getProducts(searchByName: String?, perPage: Int, offset: Int): List<ProductModel> {
        return productsRepository.getProducts(searchByName, perPage, offset, ORDER_BY)
    }
}
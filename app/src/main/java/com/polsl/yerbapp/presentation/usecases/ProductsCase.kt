package com.polsl.yerbapp.presentation.usecases

import com.polsl.yerbapp.data.ProductsRepository

class ProductsCase(private val productsRepository: ProductsRepository) {
     fun testProducts() {
        productsRepository.getProducts()
    }
}
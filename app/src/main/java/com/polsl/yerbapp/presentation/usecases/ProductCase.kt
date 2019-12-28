package com.polsl.yerbapp.presentation.usecases

import com.polsl.yerbapp.data.repos.ManufacturersRepository
import com.polsl.yerbapp.data.repos.ProductsRepository
import com.polsl.yerbapp.data.repos.TypesRepository
import com.polsl.yerbapp.domain.models.reponse.graphql.ProductModel


class ProductCase(private val productsRepository: ProductsRepository,
                  private val manufacturersRepository: ManufacturersRepository,
                  private val typesRepository: TypesRepository )
{

    suspend fun getProduct(productId: String) = productsRepository.getProduct(productId)
    suspend fun addProduct(product: ProductModel) = productsRepository.addProduct(product)
    suspend fun getProductTypes() = typesRepository.getTypes()
    suspend fun getProductManfacturers() = manufacturersRepository.getManufacturers()
    
}
package com.polsl.yerbapp.data

import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.polsl.yerbapp.data.network.ApolloClientFactory
import com.polsl.yerbapp.domain.exceptions.UnauthorizedException
import com.polsl.yerbapp.domain.models.reponse.graphql.ManufacturerModel
import com.polsl.yerbapp.domain.models.reponse.graphql.ProductModel
import com.polsl.yerbapp.domain.models.reponse.graphql.TypeModel
import yerba.GetProductQuery
import yerba.GetProductsQuery
import java.lang.IllegalStateException

class ProductsRepository(private val apolloClientFactory: ApolloClientFactory){


    suspend fun getProducts(perPage: Int, offset: Int, orderBy: String): List<ProductModel>{
        val productsQuery = GetProductsQuery
            .builder()
            .perPage(perPage)
            .offset(offset)
            .orderBy(orderBy)
            .build()
        val apolloClient = apolloClientFactory.create()
        //delay(2000)  // for testing loaders
        try {
            val response =
                apolloClient
                    .query(productsQuery)
                    .toDeferred()
                    .await()

            if(response.hasErrors()){
                if(response.errors().first().message()?.first()?.toInt() == 401 ){
                    throw UnauthorizedException()
                }
            }
            return response.data()?.products()?.items()?.let { items ->
                items.map{ProductModel(it.id(), it.name(), it.photoUrl())}
                } ?: run {
                throw IllegalStateException()
            }

        }
        catch(ex: ApolloException){
            throw ex
        }
        catch (ex: Exception) {
            throw  ex
        }
    }

    suspend fun getProduct(productId: String): ProductModel{
        val productQuery = GetProductQuery
            .builder()
            .productId(productId)
            .build()
        val apolloClient = apolloClientFactory.create()
        //delay(2000)  // for testing loaders
        try {
            val response =
                apolloClient
                    .query(productQuery)
                    .toDeferred()
                    .await()

            if(response.hasErrors()){
                if(response.errors().first().message()?.first()?.toInt() == 401 ){
                    throw UnauthorizedException()
                }
            }
            return response.data()?.product()?.let {
                val manufacturer = ManufacturerModel(country = it.manufacturer().country(), name = it.manufacturer().name())
                val type = TypeModel(name = it.type().name())
                ProductModel(id = it.id(), name = it.name(), details = it.details(), photoUrl = it.photoUrl(), manufacturerModel = manufacturer, typeModel = type )
            }?: run {
                throw IllegalStateException()}
        }
        catch(ex: ApolloException){
            throw ex
        }
        catch (ex: Exception) {
            throw  ex
        }
    }
}
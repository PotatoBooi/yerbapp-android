package com.polsl.yerbapp.data

import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.polsl.yerbapp.data.network.ApolloClientFactory
import com.polsl.yerbapp.domain.exceptions.UnauthorizedException
import com.polsl.yerbapp.domain.models.reponse.graphql.ProductModel
import yerba.GetProductsQuery
import java.lang.IllegalStateException

class ProductsRepository(private val apolloClientFactory: ApolloClientFactory){


    suspend fun getProducts(perPage: Int, offset: Int): List<ProductModel>{
        val productsQuery = GetProductsQuery
            .builder()
            .perPage(perPage)
            .offset(offset)
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
            return response.data()?.products()?.let { items ->
                items.map{ProductModel(it.id(), it.name(), it.photoUrl(),
                        null, null, null)}
                } ?: run {
                throw IllegalStateException()
            }

        }
        catch(apolloException: ApolloException){
            throw apolloException
        }
        catch (ex: Exception) {
            throw  ex
        }
    }
}
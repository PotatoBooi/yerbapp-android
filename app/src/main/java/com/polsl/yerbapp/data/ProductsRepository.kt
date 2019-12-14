package com.polsl.yerbapp.data

import android.accounts.NetworkErrorException
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.polsl.yerbapp.data.device.SharedPreferencesManager
import com.polsl.yerbapp.data.network.ApolloClientFactory
import com.polsl.yerbapp.data.network.ConnectivityInterceptor
import yerba.GetProductsQuery
import com.polsl.yerbapp.domain.models.ProductModel
import java.lang.IllegalStateException

class ProductsRepository(private val apolloClientFactory: ApolloClientFactory){


    suspend fun getProducts(): List<ProductModel>{
        val productsQuery = GetProductsQuery
            .builder()
            .build()
        val apolloClient = apolloClientFactory.create()
        try {
            val productsData =
                apolloClient
                    .query(productsQuery)
                    .toDeferred()
                    .await()
            return productsData.data()?.products()?.let { items ->
                items.map{ProductModel(it.id(), it.name(), it.photoUrl(),
                    null, null, null)}
            } ?: run {
                throw IllegalStateException()
            }
        }catch(apollo: ApolloException){
            throw NetworkErrorException() as Throwable
        }
    }
}
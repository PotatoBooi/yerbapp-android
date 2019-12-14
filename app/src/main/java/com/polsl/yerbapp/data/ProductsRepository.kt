package com.polsl.yerbapp.data

import android.accounts.NetworkErrorException
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.polsl.yerbapp.data.network.GraphqlService
import yerba.GetProductsQuery
import com.polsl.yerbapp.domain.models.ProductModel
import yerba.GetProductQuery
import java.lang.IllegalStateException

class ProductsRepository(private val graphqlService: GraphqlService){


    suspend fun getProducts(): List<ProductModel>{
        val productsQuery = GetProductsQuery
            .builder()
            .build()

        try {
            val productsData =
                graphqlService.getClient().query(productsQuery).toDeferred().await()
            return productsData.data()?.products()?.let { items ->
                items.map{ProductModel(it.id(), it.name(), it.photoUrl(),
                    null, null, null)}
            } ?: run {
                throw IllegalStateException()
            }
        }catch(apollo: ApolloException){
            throw NetworkErrorException()
        }
    }
}
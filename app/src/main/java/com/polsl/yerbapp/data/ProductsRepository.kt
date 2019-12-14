package com.polsl.yerbapp.data

import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.polsl.yerbapp.data.network.GraphqlService
import yerba.GetProductsQuery

class ProductsRepository(private val graphqlService: GraphqlService){

    fun getProducts(){

        graphqlService.getClient().query(GetProductsQuery())
            .enqueue(object : ApolloCall.Callback<GetProductsQuery.Data>(){

                override fun onResponse(response: Response<GetProductsQuery.Data>) {
                    if (!response.hasErrors()) {
                        // Here response().data() contains the data you requested
                        Log.d("TEST", "Response data:  ${response.data()}")

                    } else {
                        Log.d("TEST", "Request Failure ${response.errors()}")
                    }
                }
                override fun onFailure(e: ApolloException) {
                    e.printStackTrace()
                }
            })
    }

}
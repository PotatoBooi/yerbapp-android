package com.polsl.yerbapp.data.network

import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient


private const val GRAPHQL_API_URL = "http://157.230.108.68:3000/graphql"

interface GraphqlService{
    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): ApolloClient {
            val token = ""
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .addHeader("Authorization", "Bearer $token")
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return ApolloClient
                .builder()
                .serverUrl(GRAPHQL_API_URL)
                .okHttpClient(okHttpClient)
                .build()
        }
    }
}
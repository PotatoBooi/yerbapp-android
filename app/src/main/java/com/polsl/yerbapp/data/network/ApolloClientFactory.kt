package com.polsl.yerbapp.data.network

import com.apollographql.apollo.ApolloClient
import com.polsl.yerbapp.data.device.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient


private const val GRAPHQL_API_URL = "http://157.230.108.68:4000/graphql"

class ApolloClientFactory( private val connectivityInterceptor: ConnectivityInterceptor,
                           private val sharedPreferencesManager: SharedPreferencesManager) {

    fun create() : ApolloClient{
        return invoke(connectivityInterceptor, sharedPreferencesManager)
    }

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor,
            sharedPreferencesManager: SharedPreferencesManager
        ): ApolloClient {
            val token = sharedPreferencesManager.getUserData().accessToken
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .addHeader("Authorization", "Bearer $token")  // TODO refresh token!
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




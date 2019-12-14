package com.polsl.yerbapp.data.network

import com.apollographql.apollo.ApolloClient
import com.polsl.yerbapp.data.device.SharedPreferencesManager
import com.polsl.yerbapp.domain.models.LoginDto
import com.polsl.yerbapp.domain.models.LoginResponse
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.http.Body


private const val GRAPHQL_API_URL = "http://157.230.108.68:4000/graphql"

class GraphqlService(private val connectivityInterceptor: ConnectivityInterceptor, private val sharedPreferencesManager: SharedPreferencesManager){
// it is more like factory
    // make not authorized too
    fun getClient(): ApolloClient{
        return invoke(connectivityInterceptor, sharedPreferencesManager)
    }

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor, sharedPreferencesManager: SharedPreferencesManager): ApolloClient {
            val token = sharedPreferencesManager.getUserData().accessToken
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
            val moshi = Moshi.Builder().build()
            return ApolloClient
                .builder()
                .serverUrl(GRAPHQL_API_URL)
                .okHttpClient(okHttpClient)
                .build()
        }
    }
}
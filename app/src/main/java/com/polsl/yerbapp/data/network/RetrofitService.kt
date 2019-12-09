package com.polsl.yerbapp.data.network

import com.polsl.yerbapp.domain.models.LoginDto
import com.polsl.yerbapp.domain.models.LoginResponse
import com.polsl.yerbapp.domain.models.RegisterDto
import com.polsl.yerbapp.domain.models.RegisterResponse
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

const val API_URL = "http://157.230.108.68:4000/"

interface RetrofitService {

    @GET("auth/")
    suspend fun checkAuthorization(@Header("Authorization") authHeader: String): Response<Void>

    @POST("auth/register")
    suspend fun register(@Body register: RegisterDto): RegisterResponse

    @POST("auth/login")
    suspend fun login(@Body login: LoginDto): LoginResponse

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): RetrofitService {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            val moshi = Moshi.Builder().build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(API_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(RetrofitService::class.java)
        }
    }
}
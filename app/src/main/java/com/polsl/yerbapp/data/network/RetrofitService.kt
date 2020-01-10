package com.polsl.yerbapp.data.network

import com.polsl.yerbapp.data.device.SharedPreferencesManager
import com.polsl.yerbapp.domain.models.dto.LoginDto
import com.polsl.yerbapp.domain.models.reponse.rest.LoginResponse
import com.polsl.yerbapp.domain.models.dto.RegisterDto
import com.polsl.yerbapp.domain.models.reponse.rest.RegisterResponse
import com.polsl.yerbapp.domain.models.reponse.rest.UploadResponse
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val API_URL = "http://157.230.108.68:4000/"

interface RetrofitService {

    @GET("auth/")
    suspend fun checkAuthorization(@Header("Authorization") authHeader: String): Response<Void>

    @POST("auth/register")
    suspend fun register(@Body register: RegisterDto): RegisterResponse

    @POST("auth/login")
    suspend fun login(@Body login: LoginDto): LoginResponse

    @Multipart
    @POST("upload")
    suspend fun upload(@Part file: MultipartBody.Part): UploadResponse

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor, 
        sharedPreferencesManager: SharedPreferencesManager): RetrofitService {
            val token = sharedPreferencesManager.getUserData().accessToken
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()

                val request = token.isNotEmpty().let{
                    chain.request()
                        .newBuilder()
                        .url(url)
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                } ?: run {
                    chain.request()
                        .newBuilder()
                        .url(url)
                        .build()
                 }
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
package com.polsl.yerbapp.data

import android.accounts.NetworkErrorException
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.polsl.yerbapp.data.device.SharedPreferencesManager
import com.polsl.yerbapp.data.network.ApolloClientFactory
import com.polsl.yerbapp.data.network.RetrofitService
import com.polsl.yerbapp.domain.exceptions.InvalidCredentialsException
import com.polsl.yerbapp.domain.exceptions.UserNotFoundException
import com.polsl.yerbapp.domain.models.*
import retrofit2.HttpException
import yerba.GetProductsQuery
import java.lang.IllegalStateException

class UsersRepository(
    private val retrofitService: RetrofitService,
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val apolloClientFactory: ApolloClientFactory
) {
    suspend fun login(username: String, password: String) {
        try {
            val result = retrofitService.login(LoginDto(username, password))
            sharedPreferencesManager.saveUserData(result.id, result.role, result.accessToken)
        } catch (ex: HttpException) {
            when (ex.code()) {
                400 -> throw InvalidCredentialsException()
                404 -> throw UserNotFoundException()
            }
        } catch (ex: Exception) {
            throw  ex
        }
        //save user info to shared preferences
    }

    suspend fun register(username: String, password: String, email: String): RegisterResponse {
        try {
            return retrofitService.register(RegisterDto(username, password, email))
        } catch (ex: HttpException) {
            throw InvalidCredentialsException()

        } catch (ex: Exception) {
            throw  ex
        }
    }

    fun getCurrentUserInfo(): CurrentUserInfo = sharedPreferencesManager.getUserData()
    suspend fun checkUserAuthorized(): Boolean {
        return try {
            val token = sharedPreferencesManager.getUserData().accessToken
            val response = retrofitService.checkAuthorization("Bearer $token")
            response.code() == 200
        } catch (ex: HttpException) {
            false
        } catch (ex: Exception) {
            false
        }
    }

    fun logoutUser() {
        sharedPreferencesManager.saveUserData(-1, "", "")
    }

//    suspend fun getCurrentUser(): UserModel{
//        val productsQuery = GetProductsQuery
//            .builder()
//            .build()
//        val apolloClient = apolloClientFactory.create()
//        try {
//            val productsData =
//                apolloClient
//                    .query(productsQuery)
//                    .toDeferred()
//                    .await()
//            return productsData.data()?.products()?.let { items ->
//                items.map{
//                    ProductModel(it.id(), it.name(), it.photoUrl(),
//                        null, null, null)
//                }
//            } ?: run {
//                throw IllegalStateException()
//            }
//        }catch(apollo: ApolloException){
//            throw NetworkErrorException() as Throwable
//        }
//    }

}
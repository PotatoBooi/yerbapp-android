package com.polsl.yerbapp.data

import android.accounts.NetworkErrorException
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.polsl.yerbapp.data.device.SharedPreferencesManager
import com.polsl.yerbapp.data.network.ApolloClientFactory
import com.polsl.yerbapp.data.network.RetrofitService
import com.polsl.yerbapp.domain.exceptions.InvalidCredentialsException
import com.polsl.yerbapp.domain.exceptions.UserNotFoundException
import com.polsl.yerbapp.domain.models.dto.LoginDto
import com.polsl.yerbapp.domain.models.dto.RegisterDto
import com.polsl.yerbapp.domain.models.reponse.graphql.ProfileModel
import com.polsl.yerbapp.domain.models.reponse.graphql.UserModel
import com.polsl.yerbapp.domain.models.reponse.rest.RegisterResponse
import com.polsl.yerbapp.domain.models.reponse.sharedPreferences.CurrentUserInfo
import retrofit2.HttpException
import yerba.GetUserQuery

class UsersRepository(
    private val retrofitService: RetrofitService,
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val apolloClientFactory: ApolloClientFactory
) {
    suspend fun login(username: String, password: String) {
        try {
            val result = retrofitService.login(
                LoginDto(
                    username,
                    password
                )
            )
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
            return retrofitService.register(
                RegisterDto(
                    username,
                    password,
                    email
                )
            )
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

    suspend fun getCurrentUser(): UserModel? {
        val currUserId = getCurrentUserInfo().userId
        val userQuery = GetUserQuery
            .builder()
            .userId(currUserId.toString()) // TODO check type
            .build()
        val apolloClient = apolloClientFactory.create()
        try {
            val userData =
                apolloClient
                    .query(userQuery)
                    .toDeferred()
                    .await()
            return userData.data()?.user()?.let { u ->
                    UserModel(u.id(), u.username(), u.email(), ProfileModel(u.profile().priceImportance(), u.profile().tasteImportance(), u.profile().energyImportance(),
                        u.profile().aromaImportance(), u.profile().bitternessImportance()))
                // TODO add extra function for mapping
                } ?: run {
                throw IllegalStateException()
            }
        }catch(apollo: ApolloException){
            throw NetworkErrorException()
        }
    }

}
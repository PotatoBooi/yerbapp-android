package com.polsl.yerbapp.data

import com.polsl.yerbapp.data.device.SharedPreferencesManager
import com.polsl.yerbapp.data.network.RetrofitService
import com.polsl.yerbapp.domain.exceptions.InvalidCredentialsException
import com.polsl.yerbapp.domain.exceptions.UserNotFoundException
import com.polsl.yerbapp.domain.models.CurrentUserInfo
import com.polsl.yerbapp.domain.models.LoginDto
import com.polsl.yerbapp.domain.models.RegisterDto
import com.polsl.yerbapp.domain.models.RegisterResponse
import retrofit2.HttpException

class UsersRepository(
    private val retrofitService: RetrofitService,
    private val sharedPreferencesManager: SharedPreferencesManager
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

    fun getCurrentUser(): CurrentUserInfo = sharedPreferencesManager.getUserData()
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

}
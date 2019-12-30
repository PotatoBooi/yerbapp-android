package com.polsl.yerbapp.data.repos

import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.polsl.yerbapp.data.device.SharedPreferencesManager
import com.polsl.yerbapp.data.network.ApolloClientFactory
import com.polsl.yerbapp.data.network.RetrofitService
import com.polsl.yerbapp.domain.exceptions.InvalidCredentialsException
import com.polsl.yerbapp.domain.exceptions.NoConnectivityException
import com.polsl.yerbapp.domain.exceptions.UnauthorizedException
import com.polsl.yerbapp.domain.exceptions.UserNotFoundException
import com.polsl.yerbapp.domain.models.dto.LoginDto
import com.polsl.yerbapp.domain.models.dto.RegisterDto
import com.polsl.yerbapp.domain.models.reponse.graphql.ProfileModel
import com.polsl.yerbapp.domain.models.reponse.graphql.UserModel
import com.polsl.yerbapp.domain.models.reponse.rest.RegisterResponse
import com.polsl.yerbapp.domain.models.reponse.sharedPreferences.CurrentUserInfo
import retrofit2.HttpException
import yerba.EditUserMutation
import yerba.GetUserQuery
import yerba.type.EditUserInput

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
        }catch(ex: NoConnectivityException){
            throw ex
        }
        catch (ex: HttpException) {
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
        try {
            val apolloClient = apolloClientFactory.create()
            val response =
                apolloClient
                    .query(userQuery)
                    .toDeferred()
                    .await()

            if(response.hasErrors()){
                val errorMessage = response.errors().first().message()
                if(errorMessage == "{statusCode=401, error=Unauthorized}"){
                    throw UnauthorizedException()
                }
            }
            
            return response.data()?.user()?.let { u ->
                    UserModel(u.id(), u.username(), u.email(), ProfileModel(u.profile().priceImportance(), u.profile().tasteImportance(), u.profile().energyImportance(),
                        u.profile().aromaImportance(), u.profile().bitternessImportance()))
                // TODO add extra function for mapping
                } ?: run {
                throw IllegalStateException()
            }
        }catch(ex: ApolloException){
            throw ex
        }
        catch(ex: Exception){
            throw ex
        }
    }

    suspend fun editCurrentUser(editedUser: EditUserInput) {
        val currUserId = getCurrentUserInfo().userId
        val editUser = EditUserMutation
            .builder()
            .userId(currUserId.toString())
            .user(editedUser)
            .build()
        try {
            val apolloClient = apolloClientFactory.create()
            val response =
                apolloClient
                    .mutate(editUser)
                    .toDeferred()
                    .await()

            if(response.hasErrors()){
                if(response.errors().first().message()?.first()?.toInt() == 401 ){
                    throw UnauthorizedException()
                }
            }
        }catch(ex: ApolloException){
            throw ex
        }
        catch(ex: Exception){
            throw ex
        }
    }


}
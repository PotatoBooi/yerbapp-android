package com.polsl.yerbapp.presentation.usecases

import com.polsl.yerbapp.data.UsersRepository
import com.polsl.yerbapp.domain.models.reponse.graphql.UserModel
import com.polsl.yerbapp.domain.models.reponse.sharedPreferences.CurrentUserInfo

class GetCurrentUserCase(private val usersRepository: UsersRepository) {
    fun getCurrentUserInfo(): CurrentUserInfo? {
        val userInfo = usersRepository.getCurrentUserInfo()
        return if (userInfo.accessToken.isEmpty()) {
            null
        } else {
            userInfo
        }
    }

    suspend fun isUserAuthorized(): Boolean = usersRepository.checkUserAuthorized()
    fun logoutUser() = usersRepository.logoutUser()

    suspend fun getCurrentUser(): UserModel? = usersRepository.getCurrentUser()


}
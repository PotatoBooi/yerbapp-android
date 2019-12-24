package com.polsl.yerbapp.presentation.usecases

import com.polsl.yerbapp.data.UsersRepository
import com.polsl.yerbapp.domain.models.CurrentUserInfo
import yerba.GetUserQuery

class GetCurrentUserCase(private val usersRepository: UsersRepository) {
    fun getCurrentUserInfo(): CurrentUserInfo? {
        val user = usersRepository.getCurrentUserInfo()
        return if (user.accessToken.isEmpty()) {
            null
        } else {
            user
        }
    }

    suspend fun isUserAuthorized(): Boolean = usersRepository.checkUserAuthorized()
    fun logoutUser() = usersRepository.logoutUser()

    suspend fun getCurrentUser(){

    }
}
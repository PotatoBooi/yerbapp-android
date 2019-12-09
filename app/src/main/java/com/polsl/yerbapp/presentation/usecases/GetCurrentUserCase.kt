package com.polsl.yerbapp.presentation.usecases

import com.polsl.yerbapp.data.UsersRepository
import com.polsl.yerbapp.domain.models.CurrentUserInfo

class GetCurrentUserCase(private val usersRepository: UsersRepository) {
    fun getCurrentUser(): CurrentUserInfo? {
        val user = usersRepository.getCurrentUser()
        return if (user.accessToken.isEmpty()) {
            null
        } else {
            user
        }
    }

    suspend fun isUserAuthorized(): Boolean = usersRepository.checkUserAuthorized()
    fun logoutUser() = usersRepository.logoutUser()
}
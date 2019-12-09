package com.polsl.yerbapp.presentation.usecases

import com.polsl.yerbapp.data.UsersRepository

class LoginUserCase(private val usersRepository: UsersRepository) {
    suspend fun loginUser(username: String, password: String) {
        usersRepository.login(username, password)
    }
}
package com.polsl.yerbapp.presentation.usecases

import com.polsl.yerbapp.data.UsersRepository
import com.polsl.yerbapp.domain.models.RegisterResponse

class RegisterUserCase(private val usersRepository: UsersRepository) {
    suspend fun registerUser(login: String, email: String, password: String): RegisterResponse {
        return usersRepository.register(login, password, email)
    }
}
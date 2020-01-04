package com.polsl.yerbapp.data.repos

import com.polsl.yerbapp.data.device.SharedPreferencesManager
import com.polsl.yerbapp.data.network.ApolloClientFactory
import com.polsl.yerbapp.data.network.RetrofitService
import com.polsl.yerbapp.domain.exceptions.InvalidCredentialsException
import com.polsl.yerbapp.domain.exceptions.UserNotFoundException
import com.polsl.yerbapp.domain.models.dto.LoginDto
import retrofit2.HttpException

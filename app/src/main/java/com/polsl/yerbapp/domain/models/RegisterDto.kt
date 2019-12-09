package com.polsl.yerbapp.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterDto(
    val username: String,
    val password: String,
    val email: String
)
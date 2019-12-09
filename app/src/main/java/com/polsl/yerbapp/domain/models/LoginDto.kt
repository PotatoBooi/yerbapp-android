package com.polsl.yerbapp.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginDto(
    val username: String,
    val password: String
)
package com.polsl.yerbapp.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterResponse(
    val id: Int,
    val username: String,
    val email: String
)
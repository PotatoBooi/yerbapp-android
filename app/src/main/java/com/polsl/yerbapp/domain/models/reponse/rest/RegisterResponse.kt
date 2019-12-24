package com.polsl.yerbapp.domain.models.reponse.rest

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterResponse(
    val id: Int,
    val username: String,
    val email: String
)
package com.polsl.yerbapp.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @field:Json(name = "user_id")
    val id: Int,
    @field:Json(name = "user_role")
    val role: String,
    @field:Json(name = "access_token")
    val accessToken: String
)
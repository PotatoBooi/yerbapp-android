package com.polsl.yerbapp.domain.models

data class UserModel(
    val id: String,
    val username: String,
    val email: String,
    val avatarUrl: String?,
    val profile: ProfileModel
)


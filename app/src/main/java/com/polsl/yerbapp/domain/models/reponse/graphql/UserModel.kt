package com.polsl.yerbapp.domain.models.reponse.graphql

import com.polsl.yerbapp.domain.models.reponse.graphql.ProfileModel

data class UserModel(
    val id: String,
    val username: String,
    val email: String,
    val avatarUrl: String?,
    val profile: ProfileModel
)


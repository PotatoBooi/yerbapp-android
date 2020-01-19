package com.polsl.yerbapp.domain.models.reponse.graphql

data class UserModel(
    val id: String,
    val username: String,
    val email: String,
    val profile: ProfileModel
)


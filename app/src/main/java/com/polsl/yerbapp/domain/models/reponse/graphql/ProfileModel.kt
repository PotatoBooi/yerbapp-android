package com.polsl.yerbapp.domain.models.reponse.graphql

data class ProfileModel(
    val priceImportance: Int,
    val tasteImportance: Int,
    val energyImportance: Int,
    val aromaImportance: Int,
    val bitternessImportance: Int
)

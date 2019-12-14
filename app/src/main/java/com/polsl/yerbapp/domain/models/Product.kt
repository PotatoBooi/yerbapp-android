package com.polsl.yerbapp.domain.models

data class Product(
    val id: String,
    val name: String,
    val photoUrl: String?,
    val details: String?,
    val manufacturer: Manufacturer?,
    val type: Type?
)
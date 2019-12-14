package com.polsl.yerbapp.domain.models

data class ProductModel(
    val id: String,
    val name: String,
    val photoUrl: String?,
    val details: String?,
    val manufacturerModel: ManufacturerModel?,
    val typeModel: TypeModel?
)
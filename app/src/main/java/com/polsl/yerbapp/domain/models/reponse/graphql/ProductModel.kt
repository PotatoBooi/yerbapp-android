package com.polsl.yerbapp.domain.models.reponse.graphql

data class ProductModel(
    val id: String,
    val name: String,
    val photoUrl: String? = null,
    val details: String? = null,
    val manufacturerModel: ManufacturerModel? = null,
    val typeModel: TypeModel? = null
)
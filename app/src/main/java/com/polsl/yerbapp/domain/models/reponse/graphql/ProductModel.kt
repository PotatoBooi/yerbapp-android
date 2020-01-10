package com.polsl.yerbapp.domain.models.reponse.graphql

data class ProductModel(
    val id: String,
    val name: String,
    val overallAverage: Double,
    val photoUrl: String? = null,
    val aromaAverage: Double? = null,
    val bitternessAverage: Double? = null,
    val energyAverage: Double? = null,
    val priceAverage: Double? = null,
    val tasteAverage: Double? = null,
    val details: String? = null,
    val manufacturerModel: ManufacturerModel? = null,
    val typeModel: TypeModel? = null
)
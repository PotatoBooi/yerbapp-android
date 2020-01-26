package com.polsl.yerbapp.domain.models.reponse.graphql

data class ProductModel(
    val id: String,
    val name: String,
    val overallAverage: Float,
    val photoUrl: String? = null,
    val aromaAverage: Float? = null,
    val bitternessAverage: Float? = null,
    val energyAverage: Float? = null,
    val priceAverage: Float? = null,
    val tasteAverage: Float? = null,
    val details: String? = null,
    val manufacturerModel: ManufacturerModel? = null,
    val typeModel: TypeModel? = null,
    val numberOfReviews: Int? = 0
)

package com.polsl.yerbapp.domain.models.reponse.graphql

data class ManufacturerModel(
    val id: String? = null,
    val name: String,
    val country: String
)  {
    override fun toString(): String {
        return String.format("%s, %s", name, country)
    }
}
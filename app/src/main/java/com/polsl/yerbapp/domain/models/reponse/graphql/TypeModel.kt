package com.polsl.yerbapp.domain.models.reponse.graphql

data class TypeModel(
    val id: Int? = null,
    val name: String
){
    override fun toString(): String {
        return name
    }
}
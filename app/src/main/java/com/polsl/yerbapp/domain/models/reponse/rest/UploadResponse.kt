package com.polsl.yerbapp.domain.models.reponse.rest

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UploadResponse(
    @field:Json(name = "url")
    val url: String
)
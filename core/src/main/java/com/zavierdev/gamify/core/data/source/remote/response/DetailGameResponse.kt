package com.zavierdev.gamify.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailGameResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("rating")
    val rating: Float,

    @field:SerializedName("background_image")
    val backgroundImage: String,

    @field:SerializedName("description")
    val description: String
)

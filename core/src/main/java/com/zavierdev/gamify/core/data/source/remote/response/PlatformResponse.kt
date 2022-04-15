package com.zavierdev.gamify.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PlatformResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String
)
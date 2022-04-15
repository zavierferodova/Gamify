package com.zavierdev.gamify.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PivotPlatformResponse(
    @field:SerializedName("platform")
    val platform: PlatformResponse
)

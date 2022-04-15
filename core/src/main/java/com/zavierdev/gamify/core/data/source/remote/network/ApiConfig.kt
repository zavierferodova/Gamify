package com.zavierdev.gamify.core.data.source.remote.network

import com.zavierdev.gamify.core.BuildConfig

object ApiConfig {
    val API_KEY: String = BuildConfig.RAWG_API_KEY
    val HOSTNAME: String = "api.rawg.io"
    val BASE_URL: String = "https://${HOSTNAME}"
}
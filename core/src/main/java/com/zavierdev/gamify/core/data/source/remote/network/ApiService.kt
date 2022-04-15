package com.zavierdev.gamify.core.data.source.remote.network

import com.zavierdev.gamify.core.data.source.remote.response.DetailGameResponse
import com.zavierdev.gamify.core.data.source.remote.response.ListGameResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/api/games")
    suspend fun getList(
        @Query("key") apiKey: String
    ): ListGameResponse

    @GET("/api/games/{id}")
    suspend fun getDetail(
        @Path("id") id: Int,
        @Query("key") apiKey: String
    ): DetailGameResponse
}
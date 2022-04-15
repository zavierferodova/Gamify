package com.zavierdev.gamify.core.data.source.remote

import android.util.Log
import com.zavierdev.gamify.core.data.source.remote.network.ApiConfig
import com.zavierdev.gamify.core.data.source.remote.network.ApiResponse
import com.zavierdev.gamify.core.data.source.remote.network.ApiService
import com.zavierdev.gamify.core.data.source.remote.response.DetailGameResponse
import com.zavierdev.gamify.core.data.source.remote.response.GameResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getGames(): Flow<ApiResponse<List<GameResponse>>> {
        return flow {
            try {
                val response = apiService.getList(ApiConfig.API_KEY)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailGame(id: Int): Flow<ApiResponse<DetailGameResponse>> {
        return flow {
            try {
                val response = apiService.getDetail(id, ApiConfig.API_KEY)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}
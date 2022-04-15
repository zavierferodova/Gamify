package com.zavierdev.gamify.core.data

import com.zavierdev.gamify.core.data.source.local.LocalDataSource
import com.zavierdev.gamify.core.data.source.remote.RemoteDataSource
import com.zavierdev.gamify.core.data.source.remote.network.ApiResponse
import com.zavierdev.gamify.core.data.source.remote.response.GameResponse
import com.zavierdev.gamify.core.domain.model.Game
import com.zavierdev.gamify.core.domain.repository.IGameRepository
import com.zavierdev.gamify.core.utils.AppExecutors
import com.zavierdev.gamify.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GameRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IGameRepository {
    override fun getGames(): Flow<Resource<List<Game>>> =
        object : NetworkBoundResource<List<Game>, List<GameResponse>>() {
            override fun loadFromDB(): Flow<List<Game>> =
                localDataSource.getGame().map {
                    DataMapper.mapEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Game>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<GameResponse>>> =
                remoteDataSource.getGames()

            override suspend fun saveCallResult(data: List<GameResponse>) {
                val games = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGame(games)
            }
        }.asFlow()


    override fun getDetailGame(id: Int): Flow<Resource<Game>> = flow {
        emit(Resource.Loading())
        localDataSource.getDetailGame(id).collect { dbGame ->
            val gameDomain = Game(
                id = dbGame.id,
                name = dbGame.name,
                rating = dbGame.rating,
                backgroundImage = dbGame.backgroundImage,
                description = dbGame.description,
                isFavorite = dbGame.isFavorite
            )

            if (dbGame.description == "") {
                remoteDataSource.getDetailGame(id).collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            val responseGame = response.data
                            dbGame.description = responseGame.description
                            localDataSource.updateGame(dbGame)

                            gameDomain.description = responseGame.description
                            emit(Resource.Success(gameDomain))
                        }
                        is ApiResponse.Error -> {
                            emit(Resource.Error(response.errorMessage))
                        }
                        else -> {}
                    }
                }
            } else {
                emit(Resource.Success(gameDomain))
            }
        }
    }

    override fun getFavoriteGame(): Flow<List<Game>> =
        localDataSource.getFavoriteGame().map {
            DataMapper.mapEntitiesToDomain(it)
        }

    override fun setFavoriteGame(game: Game, state: Boolean) {
        val gameEntity = DataMapper.mapDomainToEntity(game)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteGame(gameEntity, state)
        }
    }
}
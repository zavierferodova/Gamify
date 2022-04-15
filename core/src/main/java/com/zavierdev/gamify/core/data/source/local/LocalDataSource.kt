package com.zavierdev.gamify.core.data.source.local

import com.zavierdev.gamify.core.data.source.local.entity.GameEntity
import com.zavierdev.gamify.core.data.source.local.room.GameDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val gameDao: GameDao) {
    fun getGame(): Flow<List<GameEntity>> = gameDao.getGame()

    fun getDetailGame(id: Int) = gameDao.getDetailGame(id)

    fun getFavoriteGame(): Flow<List<GameEntity>> = gameDao.getFavoriteGames()

    suspend fun insertGame(game: GameEntity) = gameDao.insertGame(game)

    suspend fun insertGame(games: List<GameEntity>) = gameDao.insertGame(games)

    suspend fun updateGame(game: GameEntity) = gameDao.updateGame(game)

    fun setFavoriteGame(game: GameEntity, state: Boolean) {
        game.isFavorite = state
        gameDao.updateFavoriteGame(game)
    }
}
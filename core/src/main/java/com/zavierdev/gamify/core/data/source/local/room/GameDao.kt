package com.zavierdev.gamify.core.data.source.local.room

import androidx.room.*
import com.zavierdev.gamify.core.data.source.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game WHERE 1")
    fun getGame(): Flow<List<GameEntity>>

    @Query("SELECT * FROM game WHERE id = :id")
    fun getDetailGame(id: Int): Flow<GameEntity>

    @Query("SELECT * FROM game WHERE isFavorite = 1")
    fun getFavoriteGames(): Flow<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: List<GameEntity>)

    @Update
    suspend fun updateGame(game: GameEntity)

    @Update
    fun updateFavoriteGame(game: GameEntity)
}
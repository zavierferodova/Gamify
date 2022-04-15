package com.zavierdev.gamify.core.domain.usecase

import com.zavierdev.gamify.core.data.Resource
import com.zavierdev.gamify.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun getGames(): Flow<Resource<List<Game>>>

    fun getDetailGame(id: Int): Flow<Resource<Game>>

    fun getFavoriteGame(): Flow<List<Game>>

    fun setFavoriteGame(game: Game, state: Boolean)
}
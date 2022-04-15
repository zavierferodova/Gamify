package com.zavierdev.gamify.core.domain.usecase

import com.zavierdev.gamify.core.data.Resource
import com.zavierdev.gamify.core.domain.model.Game
import com.zavierdev.gamify.core.domain.repository.IGameRepository
import kotlinx.coroutines.flow.Flow

class GameInteractor(private val gameRepository: IGameRepository) : GameUseCase {
    override fun getGames(): Flow<Resource<List<Game>>> = gameRepository.getGames()

    override fun getDetailGame(id: Int): Flow<Resource<Game>> =
        gameRepository.getDetailGame(id)

    override fun getFavoriteGame(): Flow<List<Game>> = gameRepository.getFavoriteGame()

    override fun setFavoriteGame(game: Game, state: Boolean) =
        gameRepository.setFavoriteGame(game, state)
}
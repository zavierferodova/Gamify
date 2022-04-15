package com.zavierdev.gamify.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zavierdev.gamify.core.domain.model.Game
import com.zavierdev.gamify.core.domain.usecase.GameUseCase

class DetailGameViewModel(private val gameUseCase: GameUseCase) : ViewModel() {
    fun setFavoriteTourism(game: Game, state: Boolean) = gameUseCase.setFavoriteGame(game, state)
    fun getDetailGame(id: Int) = gameUseCase.getDetailGame(id).asLiveData()
}
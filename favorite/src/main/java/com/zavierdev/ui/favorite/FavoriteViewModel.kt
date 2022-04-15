package com.zavierdev.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zavierdev.gamify.core.domain.usecase.GameUseCase

class FavoriteViewModel(gameUseCase: GameUseCase) : ViewModel() {
    val game = gameUseCase.getFavoriteGame().asLiveData()
}
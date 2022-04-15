package com.zavierdev.gamify.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zavierdev.gamify.core.domain.usecase.GameUseCase

class HomeViewModel(gameUseCase: GameUseCase) : ViewModel() {
    val game = gameUseCase.getGames().asLiveData()
}
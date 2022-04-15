package com.zavierdev.gamify.di

import com.zavierdev.gamify.core.domain.usecase.GameInteractor
import com.zavierdev.gamify.core.domain.usecase.GameUseCase
import com.zavierdev.gamify.ui.detail.DetailGameViewModel
import com.zavierdev.gamify.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GameUseCase> { GameInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailGameViewModel(get()) }
}
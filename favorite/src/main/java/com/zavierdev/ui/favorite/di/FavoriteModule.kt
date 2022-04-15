package com.zavierdev.ui.favorite.di

import com.zavierdev.ui.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel {
        FavoriteViewModel(get())
    }
}
package com.zavierdev.gamify.core.utils

import com.zavierdev.gamify.core.data.source.local.entity.GameEntity
import com.zavierdev.gamify.core.data.source.remote.response.GameResponse
import com.zavierdev.gamify.core.domain.model.Game

object DataMapper {
    fun mapResponsesToEntities(input: List<GameResponse>): List<GameEntity> =
        input.map {
            GameEntity(
                id = it.id,
                name = it.name,
                rating = it.rating,
                backgroundImage = it.backgroundImage,
                description = "",
                isFavorite = false
            )
        }

    fun mapEntitiesToDomain(input: List<GameEntity>): List<Game> =
        input.map {
            Game(
                id = it.id,
                name = it.name,
                rating = it.rating,
                backgroundImage = it.backgroundImage,
                description = it.description,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Game): GameEntity =
        GameEntity(
            id = input.id,
            name = input.name,
            rating = input.rating,
            backgroundImage = input.backgroundImage,
            description = input.description,
            isFavorite = input.isFavorite
        )
}
package com.zavierdev.gamify.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "rating")
    val rating: Float,

    @ColumnInfo(name = "backgroundImage")
    val backgroundImage: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
)

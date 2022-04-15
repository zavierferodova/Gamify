package com.zavierdev.gamify.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [com.zavierdev.gamify.core.data.source.local.entity.GameEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}
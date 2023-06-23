package com.itstor.paimonguide.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCharacterEntity::class], version = 1, exportSchema = false)
abstract class FavoriteCharacterDatabase : RoomDatabase() {
    abstract fun favoriteCharacterDao(): FavoriteCharacterDao
}
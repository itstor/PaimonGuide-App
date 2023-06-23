package com.itstor.paimonguide.di

import android.content.Context
import androidx.room.Room
import com.itstor.paimonguide.data.local.FavoriteCharacterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideFavoriteCharacterDatabase(@ApplicationContext appContext: Context): FavoriteCharacterDatabase {
        return Room.databaseBuilder(
            appContext,
            FavoriteCharacterDatabase::class.java, "favorite_character_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideFavoriteCharacterDao(database: FavoriteCharacterDatabase) = database.favoriteCharacterDao()
}
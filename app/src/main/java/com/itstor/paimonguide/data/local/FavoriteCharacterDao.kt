package com.itstor.paimonguide.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteCharacter(favoriteCharacterEntity: FavoriteCharacterEntity)

    @Query("SELECT * FROM favorite_characters")
    fun getAllFavoriteCharacters(): Flow<List<FavoriteCharacterEntity>>

    @Query("SELECT * FROM favorite_characters WHERE id = :id")
    fun getFavoriteCharacter(id: String): Flow<FavoriteCharacterEntity>

    @Query("SELECT EXISTS(SELECT * FROM favorite_characters WHERE id = :id)")
    fun isFavoriteCharacter(id: String): Flow<Boolean>

    @Query("DELETE FROM favorite_characters WHERE id = :id")
    suspend fun deleteFavoriteCharacter(id: String)
}
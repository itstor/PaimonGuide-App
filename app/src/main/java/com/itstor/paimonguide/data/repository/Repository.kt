package com.itstor.paimonguide.data.repository

import com.itstor.paimonguide.data.local.FavoriteCharacterDao
import com.itstor.paimonguide.data.local.FavoriteCharacterEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val favoriteCharacterDao: FavoriteCharacterDao) {
    suspend fun addFavoriteCharacter(favoriteCharacterEntity: FavoriteCharacterEntity) = favoriteCharacterDao.addFavoriteCharacter(favoriteCharacterEntity)
    fun getAllFavoriteCharacters() = favoriteCharacterDao.getAllFavoriteCharacters()
    fun getFavoriteCharacter(id: String) = favoriteCharacterDao.getFavoriteCharacter(id)
    fun isFavoriteCharacter(id: String) = favoriteCharacterDao.isFavoriteCharacter(id)
    suspend fun deleteFavoriteCharacter(id: String) = favoriteCharacterDao.deleteFavoriteCharacter(id)
}
package com.itstor.paimonguide.ui.screens.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itstor.paimonguide.common.Result
import com.itstor.paimonguide.data.local.FavoriteCharacterEntity
import com.itstor.paimonguide.data.repository.Repository
import com.itstor.paimonguide.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val application: Application, private val repository: Repository): ViewModel() {
    private val _characterData = MutableLiveData<Result<Character?>>()
    val characterData: LiveData<Result<Character?>> get() = _characterData

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun isFavoriteCharacter(id: String) {
        viewModelScope.launch {
            repository.isFavoriteCharacter(id).collect { isFavorite ->
                _isFavorite.value = isFavorite
            }
        }
    }

    fun toggleFavoriteCharacter(id: String) {
        viewModelScope.launch {
            val isFavorite = repository.isFavoriteCharacter(id).first()
            if (isFavorite) {
                repository.deleteFavoriteCharacter(id)
            } else {
                repository.addFavoriteCharacter(FavoriteCharacterEntity(id))
            }
            _isFavorite.value = !isFavorite
        }
    }

    fun loadCharacterById(id: String) {
        _characterData.value = Result.Loading

        viewModelScope.launch {
            try {
                val jsonFile: String = withContext(Dispatchers.IO) {
                    application.assets.open("character_data.json").bufferedReader().use {
                        it.readText()
                    }
                }

                val charactersList: List<Character> = Gson().fromJson(jsonFile, object : TypeToken<List<Character>>() {}.type)
                val character = charactersList.find { it.id == id }

                _characterData.value = Result.Success(character)
            } catch (exception: Exception) {
                _characterData.value = Result.Error(exception.message ?: "Unknown error occurred")
            }
        }
    }
}
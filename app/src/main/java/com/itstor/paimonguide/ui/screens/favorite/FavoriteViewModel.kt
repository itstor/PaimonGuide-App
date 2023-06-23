package com.itstor.paimonguide.ui.screens.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itstor.paimonguide.common.Result
import com.itstor.paimonguide.data.repository.Repository
import com.itstor.paimonguide.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: Repository, private val application: Application) : ViewModel() {
    private val _favoriteCharacter = MutableLiveData<Result<List<Character>>>()
    val favoriteCharacter: LiveData<Result<List<Character>>> get() = _favoriteCharacter

    fun loadFavoriteCharacter() {
        _favoriteCharacter.value = Result.Loading

        viewModelScope.launch {
            try {
                val jsonFile: String = withContext(Dispatchers.IO) {
                    application.assets.open("character_data.json").bufferedReader().use {
                        it.readText()
                    }
                }

                val characterList: List<Character> = Gson().fromJson(jsonFile, object : TypeToken<List<Character>>() {}.type)
                val favoriteCharacterIdList = repository.getAllFavoriteCharacters().first()

                val favoriteCharacterList = mutableListOf<Character>()

                favoriteCharacterIdList.forEach { favoriteCharacter ->
                    characterList.forEach { character ->
                        if (favoriteCharacter.id == character.id) {
                            favoriteCharacterList.add(character)
                        }
                    }
                }

                _favoriteCharacter.value = Result.Success(favoriteCharacterList)
            } catch (exception: Exception) {
                _favoriteCharacter.value = Result.Error(exception.message ?: "Unknown error occurred")
            }
        }
    }
}
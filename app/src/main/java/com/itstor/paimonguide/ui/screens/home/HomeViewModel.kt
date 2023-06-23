package com.itstor.paimonguide.ui.screens.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itstor.paimonguide.common.Result
import com.itstor.paimonguide.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val application: Application,
): ViewModel() {
    private val _characterData = MutableLiveData<Result<List<Character>>>()
    val characterData: LiveData<Result<List<Character>>> get() = _characterData

    init {
        loadData()
    }

    private fun loadData() {
        _characterData.value = Result.Loading

        viewModelScope.launch {
            try {
                val jsonFile: String = withContext(Dispatchers.IO) {
                    application.assets.open("character_data.json").bufferedReader().use {
                        it.readText()
                    }
                }

                val characterList: List<Character> = Gson().fromJson(jsonFile, object : TypeToken<List<Character>>() {}.type)
                _characterData.value = Result.Success(characterList)
            } catch (exception: Exception) {
                _characterData.value = Result.Error(exception.message ?: "Unknown error occurred")
            }
        }
    }
}
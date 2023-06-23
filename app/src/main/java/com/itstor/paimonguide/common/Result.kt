package com.itstor.paimonguide.common

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorMessage: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
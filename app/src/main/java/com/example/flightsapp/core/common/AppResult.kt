package com.example.flightsapp.core.common

sealed interface AppResult<out R> {
    data class Success<out T>(val value: T) : AppResult<T>
    data class Error(val error: UiText) : AppResult<Nothing>
}

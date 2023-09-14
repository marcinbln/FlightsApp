package com.example.flightsapp.core.common

sealed interface WorkResult<out R> {
    data class Success<out T>(val value: T) : WorkResult<T>
    data class Error(val error: UiText) : WorkResult<Nothing>
}

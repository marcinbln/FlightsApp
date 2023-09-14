package com.example.flightsapp.core.data.di

import javax.inject.Qualifier

@Target(
    AnnotationTarget.PROPERTY,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.EXPRESSION
)
@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class Dispatcher(@Suppress("unused") val appDispatcher: AppDispatchers)

enum class AppDispatchers {
    IO
}

package com.example.flightsapp.core.common

import androidx.annotation.StringRes

sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    data class ResourceString(@StringRes val resId: Int) : UiText
}

@file:Suppress("UnusedReceiverParameter")

package com.example.flightsapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.errorprone.annotations.Immutable

@Immutable
data class CustomColorsPalette(
    val extraContainerColor: Color = Color.Unspecified,
    val extraTextColor: Color = Color.Unspecified

)

val LightExtraColor1 = Blue200
val LightExtraColor2 = Blue800

val DarkExtraColor1 = Grey200
val DarkExtraColor2 = Blue500

val LightCustomColorsPalette = CustomColorsPalette(
    extraContainerColor = LightExtraColor1,
    extraTextColor = LightExtraColor2
)

val DarkCustomColorsPalette = CustomColorsPalette(
    extraContainerColor = DarkExtraColor1,
    extraTextColor = DarkExtraColor2
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }

val MaterialTheme.customColorsPalette: CustomColorsPalette
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColorsPalette.current

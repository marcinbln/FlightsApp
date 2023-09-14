package com.example.flightsapp.ui.screens.settings.components

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.flightsapp.ui.theme.Grey500

@Composable
internal fun Switch(checkedState: Boolean, onCheckedChange: () -> Unit) {
    Switch(
        checked = checkedState,
        onCheckedChange = {
            onCheckedChange()
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            uncheckedThumbColor = Color.White,
            checkedTrackColor = Color.Black,
            uncheckedTrackColor = Grey500,
            uncheckedBorderColor = Grey500,
            checkedBorderColor = Color.Black
        )
    )
}

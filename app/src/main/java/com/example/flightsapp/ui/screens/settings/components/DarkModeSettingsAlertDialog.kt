package com.example.flightsapp.ui.screens.settings.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R
import com.example.flightsapp.core.model.DarkThemeConfig

@Composable
internal fun DarkModeSettingsAlertDialog(
    dialogTitle: String,
    selectedOption: DarkThemeConfig,
    onSelected: (DarkThemeConfig) -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        icon = {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = R.drawable.ic_dark_mode),
                contentDescription = null
            )
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            RadioGroup(
                selectedOption = selectedOption,
                onSelected = onSelected,
            )
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {},
    )
}

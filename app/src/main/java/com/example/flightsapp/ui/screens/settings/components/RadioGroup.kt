package com.example.flightsapp.ui.screens.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R
import com.example.flightsapp.core.model.DarkThemeConfig

@Composable
internal fun RadioGroup(
    selectedOption: DarkThemeConfig,
    onSelected: (DarkThemeConfig) -> Unit
) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedOption == DarkThemeConfig.FOLLOW_SYSTEM,
                onClick = { onSelected(DarkThemeConfig.FOLLOW_SYSTEM) }
            )
            Text(
                text = stringResource(R.string.dark_mode_config_system_default),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedOption == DarkThemeConfig.DARK,
                onClick = { onSelected(DarkThemeConfig.DARK) }
            )
            Text(
                text = stringResource(R.string.dark_mode_config_dark),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedOption == DarkThemeConfig.LIGHT,
                onClick = { onSelected(DarkThemeConfig.LIGHT) }
            )
            Text(
                text = stringResource(R.string.dark_mode_config_light),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

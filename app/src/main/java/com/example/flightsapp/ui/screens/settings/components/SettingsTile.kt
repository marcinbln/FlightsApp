package com.example.flightsapp.ui.screens.settings.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R
import com.example.flightsapp.ui.theme.FlightsAppTheme
import com.example.flightsapp.ui.theme.customColorsPalette

@Composable
internal fun SettingsTile(
    modifier: Modifier = Modifier,
    leadingIcon: Int,
    settingName: Int,
    content: @Composable () -> Unit,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .clickable(
                enabled = enabled,
                onClick = { onClick() }
            )
            .height(88.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.customColorsPalette.extraContainerColor,
            contentColor = Color.Black,
        )
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = leadingIcon),
                    contentDescription = null
                )

                Spacer(modifier = modifier.width(18.dp))

                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = settingName),
                    style = MaterialTheme.typography.labelLarge,
                )

                content()
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun SettingsTilePreview() {
    FlightsAppTheme {
        SettingsTile(
            leadingIcon = R.drawable.ic_dark_mode,
            settingName = R.string.settings_dark_mode,
            content = {
                Switch(checkedState = true, onCheckedChange = {})
            },
            onClick = {}
        )
    }
}

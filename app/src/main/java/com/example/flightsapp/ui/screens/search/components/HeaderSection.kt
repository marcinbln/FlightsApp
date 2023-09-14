package com.example.flightsapp.ui.screens.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R

@Composable
internal fun HeaderSection(
    modifier: Modifier = Modifier,
    onSettingsClicked: () -> Unit
) {
    Column(modifier = modifier.padding(start = 42.dp, end = 38.dp)) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.search_hello_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = onSettingsClicked) {
                Icon(
                    modifier = modifier.size(32.dp),
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = null
                )
            }
        }

        Text(
            text = stringResource(R.string.search_subtitle),
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Preview
@Composable
private fun HeaderSectionPreview() {
    HeaderSection(onSettingsClicked = {})
}

package com.example.flightsapp.ui.screens.results.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun SummaryRow(roundTrip: Boolean) {
    Row {
        Text(text = "1 adult", style = MaterialTheme.typography.bodySmall)
        DotSpacer()
        Text(
            text = if (roundTrip) {
                "Return"
            } else {
                "One way"
            },
            style = MaterialTheme.typography.bodySmall
        )
        DotSpacer()
        Text(text = "Economy Class", style = MaterialTheme.typography.bodySmall)
        DotSpacer()
        Text(text = "EUR", style = MaterialTheme.typography.bodySmall)
    }
}

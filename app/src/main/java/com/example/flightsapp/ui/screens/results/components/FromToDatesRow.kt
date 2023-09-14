package com.example.flightsapp.ui.screens.results.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.flightsapp.ui.screens.results.ResultsState

@Composable
internal fun FromToDatesRow(
    resultsState: ResultsState,
    roundTrip: Boolean
) {
    Row {
        Text(text = resultsState.fromDate)
        Text(text = if (roundTrip) " - " else "")
        Text(text = resultsState.toDate)
    }
}

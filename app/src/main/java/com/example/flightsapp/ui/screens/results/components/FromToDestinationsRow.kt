package com.example.flightsapp.ui.screens.results.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R
import com.example.flightsapp.ui.screens.results.ResultsState

@Composable
internal fun FromToDestinationsRow(
    modifier: Modifier = Modifier,
    resultsState: ResultsState
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = resultsState.fromAirport,
            style = MaterialTheme.typography.headlineSmall,
        )

        Spacer(modifier = modifier.width(8.dp))

        if (resultsState.isRoundTrip) {
            Icon(
                modifier = modifier.size(20.dp),
                painter = painterResource(R.drawable.ic_swap),
                contentDescription = null
            )
        } else {
            Icon(
                modifier = modifier.size(20.dp),
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = null
            )
        }

        Spacer(modifier = modifier.width(8.dp))

        Text(
            text = resultsState.toAirport,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

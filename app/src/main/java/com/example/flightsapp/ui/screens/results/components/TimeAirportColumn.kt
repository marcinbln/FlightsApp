package com.example.flightsapp.ui.screens.results.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsapp.core.common.toSeparateDateAndTime
import com.example.flightsapp.ui.theme.FlightsAppTheme

@Composable
internal fun TimeAirportColumn(
    modifier: Modifier = Modifier,
    time: String,
    airportCode: String
) {
    val (dateFormatted, timeFormatted) = time.toSeparateDateAndTime()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = timeFormatted,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = dateFormatted,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(0.8f)
        )

        Spacer(modifier = modifier.height(6.dp))
        Surface(
            shape = RoundedCornerShape(40),
            color = MaterialTheme.colorScheme.surfaceTint

        ) {
            Text(
                modifier = modifier.padding(
                    horizontal = 6.dp,
                    vertical = 2.dp
                ),
                text = airportCode,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = modifier.height(8.dp))
    }
}

@Preview
@Composable
private fun TimeAirportColumnPreview() {
    FlightsAppTheme {
        TimeAirportColumn(time = "2023-09-28T08:30:00", airportCode = "SYD")
    }
}

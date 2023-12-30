package com.example.flightsapp.ui.screens.results.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.flightsapp.core.model.FlightOffer
import com.example.flightsapp.ui.screens.results.ResultsState
import com.example.flightsapp.core.common.ui.FlightOfferPreviewDataProvider
import com.example.flightsapp.ui.theme.FlightsAppTheme

@Composable
internal fun ItineraryDetails(
    modifier: Modifier = Modifier,
    resultsState: ResultsState,
    itinerary: FlightOffer.Itinerary,
    name: String
) {
    val expanded = remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.width(4.dp))
        DotSpacer()
        Text(text = resultsState.toDate)
    }

    Spacer(modifier = modifier.height(14.dp))

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                10.dp
            )
        ),
        shape = RoundedCornerShape(3.dp),
        onClick = { expanded.value = !expanded.value }
    ) {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Itinerary(
                modifier = Modifier.padding(4.dp),
                item = itinerary
            )

            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(32.dp),
                imageVector = if (expanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )

            itinerary.segments.forEach {
                AnimatedVisibility(visible = expanded.value) {
                    ExpandableContent(segment = it)
                }
            }
        }
    }

    Spacer(modifier = modifier.height(10.dp))
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF2BB6EC,
    device = "spec:width=1440px,height=2960px,dpi=570"
)
@Composable
private fun ResultCardPreview(
    @PreviewParameter(FlightOfferPreviewDataProvider::class) flightOffer: FlightOffer
) {
    FlightsAppTheme {
        ItineraryDetails(
            itinerary = flightOffer.itinerary[0],
            resultsState = ResultsState(
                isLoading = false,
                fromAirport = "Ber",
                toAirport = "Lon",
                fromDate = "10 Nov 2023",
                toDate = "11 Nov 2023",
                resultsList = listOf(flightOffer, flightOffer)
            ),
            name = "Return"

        )
    }
}

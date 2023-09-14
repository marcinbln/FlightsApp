package com.example.flightsapp.ui.screens.results.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsapp.ui.components.BaseCard
import com.example.flightsapp.core.model.FlightOffer
import com.example.flightsapp.ui.screens.results.ResultsState
import com.example.flightsapp.ui.screens.results.sample
import com.example.flightsapp.ui.theme.FlightsAppTheme

@Composable
internal fun ResultCardReturnExpanded(
    modifier: Modifier = Modifier,
    flightOffer: FlightOffer,
    resultsState: ResultsState,
    onClose: () -> Unit
) {
    val roundTrip = resultsState.isRoundTrip

    BaseCard(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Header(onClose = onClose)

            Spacer(modifier = modifier.height(10.dp))

            FromToDestinationsRow(resultsState = resultsState)

            FromToDatesRow(resultsState, roundTrip)

            SummaryRow(roundTrip)

            Spacer(modifier = modifier.height(8.dp))

            PriceRow(flightOffer = flightOffer)

            Spacer(modifier = modifier.height(16.dp))

            ItineraryDetails(modifier, resultsState, flightOffer.itinerary[0], "Outbound")

            Spacer(modifier = modifier.height(10.dp))

            if (roundTrip) {
                ItineraryDetails(modifier, resultsState, flightOffer.itinerary[1], "Return")
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF2BB6EC, device = "spec:width=1440px,height=2960px,dpi=570")
@Composable
private fun ResultCardPreview() {
    FlightsAppTheme {
        ResultCardReturnExpanded(
            flightOffer = sample,
            resultsState = ResultsState(
                isLoading = false,
                fromAirport = "Ber",
                toAirport = "Lon",
                fromDate = "10 Nov 2023",
                toDate = "11 Nov 2023",
                resultsList = listOf(sample, sample)
            ),
            onClose = {}
        )
    }
}

package com.example.flightsapp.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R
import com.example.flightsapp.core.common.DevicePreviews
import com.example.flightsapp.ui.components.BaseCard
import com.example.flightsapp.ui.components.DefaultButton
import com.example.flightsapp.ui.screens.search.components.AirportTextField
import com.example.flightsapp.ui.screens.search.components.DatesFields
import com.example.flightsapp.ui.screens.search.components.HeaderSection
import com.example.flightsapp.ui.screens.search.components.TripTypeSelectors

@Composable
fun SearchRoute() {
    SearchScreen()
}

@Composable
private fun SearchScreen(modifier: Modifier = Modifier) {
    val fromAirport = remember { mutableStateOf("") }
    val toAirport = remember { mutableStateOf("") }

    val departureDate = remember { mutableStateOf("23/05/2023") }
    val returnDate = remember { mutableStateOf("24/05/2023") }

    val isRoundTrip = remember { mutableStateOf(true) }

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Bottom
    ) {
        HeaderSection()

        BaseCard(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(25.dp))

            TripTypeSelectors(isRoundTrip = isRoundTrip)

            Spacer(modifier = Modifier.height(25.dp))

            AirportTextField(
                fromAirport = fromAirport.value,
                label = R.string.search_from_label,
                icPlaneArriving = R.drawable.ic_plane_departing,
                onValueChange = { fromAirport.value = it }
            )

            Spacer(modifier = Modifier.height(25.dp))

            AirportTextField(
                fromAirport = toAirport.value,
                label = R.string.search_to_label,
                icPlaneArriving = R.drawable.ic_plane_arriving,
                onValueChange = { fromAirport.value = it }
            )

            Spacer(modifier = modifier.height(25.dp))

            DatesFields(modifier, departureDate, isRoundTrip, returnDate)

            Spacer(modifier = modifier.height(50.dp))

            DefaultButton(
                modifier = modifier.fillMaxWidth(),
                onClick = { /*TODO*/ }
            ) {
                Text(text = stringResource(R.string.search_search_flight_button))
            }

            Spacer(modifier = modifier.height(25.dp))
        }
    }
}

inline fun Modifier.conditional(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: Modifier.() -> Modifier = { this },
): Modifier = if (condition) {
    ifTrue(this)
} else {
    ifFalse(this)
}

@DevicePreviews
@Composable
private fun SearchScreenPreview() {
    SearchScreen()
}

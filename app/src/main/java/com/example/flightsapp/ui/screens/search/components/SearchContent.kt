package com.example.flightsapp.ui.screens.search.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R
import com.example.flightsapp.core.common.toUpperCase
import com.example.flightsapp.ui.components.DefaultButton
import com.example.flightsapp.ui.screens.search.SearchScreenCallback
import com.example.flightsapp.ui.screens.search.SearchState

@Composable
internal fun SearchContent(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    isRoundTrip: MutableState<Boolean>,
    viewModelCallbacks: (SearchScreenCallback) -> Unit,
    departureDate: MutableLongState,
    showDateRangePicker: MutableState<Boolean>,
    returnDate: MutableLongState,
    isReturnDateValid: Boolean,
    onSearchClicked: (
        origin: String,
        destination: String,
        departureDate: Long,
        returnDate: Long,
        isRoundTrip: Boolean
    ) -> Unit
) {
    val areKeywordsNotBlank =
        searchState.fromKeyword.isNotBlank() && searchState.destinationKeyword.isNotBlank()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 42.dp)
    ) {
        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TripTypeSelector(
                isRoundTrip.value,
                onClick = { isRoundTrip.value = true },
                R.string.search_round_trip_option
            )

            TripTypeSelector(
                !isRoundTrip.value,
                onClick = { isRoundTrip.value = false },
                R.string.search_one_way_option
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        AirportTextField(
            fromAirport = searchState.fromKeyword,
            label = R.string.search_from_label,
            icPlaneArriving = R.drawable.ic_plane_departing,
            onValueChange = { viewModelCallbacks(SearchScreenCallback.OriginValueChange(it)) },
            onValueSelected = {
                viewModelCallbacks(SearchScreenCallback.OriginAirportSelected(it))
            },
            optionsList = searchState.originSuggestions
        )

        Spacer(modifier = Modifier.height(25.dp))

        AirportTextField(
            fromAirport = searchState.destinationKeyword,
            label = R.string.search_to_label,
            icPlaneArriving = R.drawable.ic_plane_arriving,
            onValueChange = { viewModelCallbacks(SearchScreenCallback.DestinationValueChange(it)) },
            optionsList = searchState.destinationSuggestions,
            onValueSelected = {
                viewModelCallbacks(SearchScreenCallback.DestinationAirportSelected(it))
            }
        )

        Spacer(modifier = modifier.height(25.dp))

        Row(
            modifier = modifier
                .animateContentSize()
                .fillMaxWidth()
        ) {
            FromDateTextField(
                departureDate = departureDate.longValue,
                onClick = { showDateRangePicker.value = true }
            )

            AnimatedSpacer(isRoundTrip = isRoundTrip.value)

            AnimatedReturnDateTextField(
                isRoundTrip = isRoundTrip.value,
                returnDate = returnDate.longValue,
                onClick = { showDateRangePicker.value = true }
            )
        }

        Spacer(modifier = modifier.height(50.dp))

        DefaultButton(modifier = modifier.fillMaxWidth(), onClick = {
            if (areKeywordsNotBlank && isReturnDateValid) {
                onSearchClicked(
                    searchState.selectedOriginAirport?.iata
                        ?: searchState.fromKeyword.toUpperCase(),
                    searchState.selectedDestinationAirport?.iata
                        ?: searchState.destinationKeyword.toUpperCase(),
                    departureDate.longValue,
                    returnDate.longValue,
                    isRoundTrip.value
                )
            }
        }, content = {
            Text(text = stringResource(R.string.search_search_flight_button))
        })

        Spacer(modifier = modifier.height(25.dp))
    }
}

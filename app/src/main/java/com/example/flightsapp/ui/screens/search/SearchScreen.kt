package com.example.flightsapp.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flightsapp.core.common.DevicePreviews
import com.example.flightsapp.core.model.Airport
import com.example.flightsapp.ui.components.BaseCard
import com.example.flightsapp.ui.screens.search.components.HeaderSection
import com.example.flightsapp.ui.screens.search.components.RangeDatePickerDialog
import com.example.flightsapp.ui.screens.search.components.SearchContent
import com.example.flightsapp.ui.screens.search.components.SingleDatePickerDialog
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

private const val DEFAULT_TRIP_LENGTH: Long = 3

@Composable
fun SearchRoute(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onSettingsClicked: () -> Unit,
    onSearchClicked: (origin: String, String, Long, Long, Boolean) -> Unit
) {
    val searchState = searchViewModel.searchState.collectAsStateWithLifecycle()
    SearchScreen(
        searchState = searchState.value,
        onSettingsClicked = onSettingsClicked,
        onSearchClicked = onSearchClicked,
        viewModelCallbacks = searchViewModel::handleInteraction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    searchState: SearchState,
    modifier: Modifier = Modifier,
    onSettingsClicked: () -> Unit,
    onSearchClicked: (
        origin: String,
        destination: String,
        departureDate: Long,
        returnDate: Long,
        isRoundTrip: Boolean
    ) -> Unit,
    viewModelCallbacks: (SearchScreenCallback) -> Unit
) {
    val timeNow = Instant.now()
    val isRoundTrip = rememberSaveable { mutableStateOf(true) }
    val departureDate = rememberSaveable { mutableLongStateOf(timeNow.toEpochMilli()) }
    val returnDate = rememberSaveable {
        mutableLongStateOf(
            timeNow.plus(Duration.ofDays(DEFAULT_TRIP_LENGTH))
                .toEpochMilli()
        )
    }

    val isReturnDateValid = returnDate.longValue >= departureDate.longValue
    val showDatePicker = remember { mutableStateOf(false) }
    val timeMidnight = LocalDate.now()
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    val dateRangePickerState = dateRangePickerState(
        departureDate = departureDate,
        returnDate = returnDate,
        timeMidnight = timeMidnight
    )

    val singleDatePickerState =
        singleDatePickerState(departureDate = departureDate, timeMidnight = timeMidnight)

    if (showDatePicker.value) {
        if (isRoundTrip.value) {
            RangeDatePickerDialog(
                showDateRangePicker = { showDatePicker.value = it },
                dateRangePickerState = dateRangePickerState,
                departureDate = departureDate,
                returnDate = returnDate
            )
        } else {
            SingleDatePickerDialog(
                showDateRangePicker = { showDatePicker.value = it },
                datePickerState = singleDatePickerState,
                departureDate = departureDate
            )
        }
    }

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Bottom
    ) {
        HeaderSection(onSettingsClicked = onSettingsClicked)

        BaseCard {
            SearchContent(
                searchState = searchState,
                isRoundTrip = isRoundTrip,
                viewModelCallbacks = viewModelCallbacks,
                departureDate = departureDate,
                showDateRangePicker = showDatePicker,
                returnDate = returnDate,
                isReturnDateValid = isReturnDateValid,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun dateRangePickerState(
    departureDate: MutableLongState,
    returnDate: MutableLongState,
    timeMidnight: Long
) = rememberDateRangePickerState(
    initialSelectedStartDateMillis = departureDate.longValue,
    initialSelectedEndDateMillis = returnDate.longValue,
    selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis >= timeMidnight
        }
    }
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun singleDatePickerState(
    departureDate: MutableLongState,
    timeMidnight: Long
) = rememberDatePickerState(
    initialSelectedDateMillis = departureDate.longValue,
    selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis >= timeMidnight
        }
    }
)

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
    SearchScreen(
        searchState = SearchState(
            originSuggestions = SearchState.ListState(
                airportsPrev
            )
        ),
        onSettingsClicked = {},
        onSearchClicked = { _, _, _, _, _ -> }
    ) {}
}

val airportsPrev = listOf(
    Airport(
        "ATL",
        "Atlanta",
        "United States of America",
        countryCode = "UK"
    ),
    Airport(
        "ATL",
        "Atlanta",
        "United States of America",
        countryCode = "UK"
    ),
    Airport(
        "ATL",
        "Atlanta",
        "United States of America",
        countryCode = "UK"
    )
)

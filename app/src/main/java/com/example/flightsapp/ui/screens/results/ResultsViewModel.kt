package com.example.flightsapp.ui.screens.results

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsapp.core.common.UiText
import com.example.flightsapp.core.common.WorkResult
import com.example.flightsapp.core.common.toApiFormat
import com.example.flightsapp.core.common.toFormattedDate
import com.example.flightsapp.core.data.repositories.flightOffers.FlightOffersRepository
import com.example.flightsapp.core.model.FlightOffer
import com.example.flightsapp.core.model.SearchQuery
import com.example.flightsapp.ui.screens.results.navigation.ResultsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    flightOffersRepository: FlightOffersRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _resultsState = MutableStateFlow(ResultsState())
    val resultsState = _resultsState.asStateFlow()

    private val topicArgs: ResultsArgs = ResultsArgs(savedStateHandle)

    init {
        _resultsState.update {
            it.copy(
                isRoundTrip = topicArgs.isRoundTrip,
                fromAirport = topicArgs.from,
                toAirport = topicArgs.to,
                fromDate = topicArgs.departureDate.toFormattedDate("dd MMM yyyy"),
                toDate = if (topicArgs.isRoundTrip) topicArgs.returnDate.toFormattedDate("dd MMM yyyy") else ""
            )
        }

        viewModelScope.launch {
            val list = flightOffersRepository.getFlightOffers(
                SearchQuery(
                    originLocationCode = topicArgs.from,
                    destinationLocationCode = topicArgs.to,
                    departureDate = topicArgs.departureDate.toApiFormat(),
                    returnDate = if (topicArgs.isRoundTrip) topicArgs.returnDate.toApiFormat() else null,
                    adults = 1,
                    maxResults = 20
                )

            )

            when (list) {
                is WorkResult.Error -> _resultsState.update {
                    it.copy(error = list.error)
                }

                is WorkResult.Success -> _resultsState.update {
                    it.copy(resultsList = list.value)
                }
            }

            _resultsState.update {
                it.copy(isLoading = false)
            }
        }
    }
}

data class ResultsState(
    val isLoading: Boolean = true,
    val isRoundTrip: Boolean = false,
    val fromAirport: String = "",
    val toAirport: String = "",
    val fromDate: String = "",
    val toDate: String = "",
    val error: UiText? = null,
    val resultsList: List<FlightOffer> = emptyList()
)

package com.example.flightsapp.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsapp.core.common.UiText
import com.example.flightsapp.core.common.WorkResult
import com.example.flightsapp.core.data.repositories.airports.AirportsRepository
import com.example.flightsapp.core.model.Airport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("TooManyFunctions")
@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(private val airportsRepository: AirportsRepository) :
    ViewModel() {

    private val _searchState: MutableStateFlow<SearchState> = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

    init {
        collectOriginKeyword()
        collectDestinationKeyword()
    }

    private fun collectOriginKeyword() {
        viewModelScope.launch {
            _searchState.map { it.fromKeyword }
                .debounce(DEBOUNCE_TIMEOUT)
                .distinctUntilChanged()
                .collectLatest { input ->
                    if (input.isNotBlank() && _searchState.value.selectedOriginAirport == null) {
                        updateOriginListLoadingState(true)

                        when (
                            val result = airportsRepository.getAirports(input)
                                .filterNotNull()
                                .first()
                        ) {
                            is WorkResult.Success<List<Airport>> -> _searchState.update {
                                it.copy(
                                    originSuggestions = it.originSuggestions.copy(list = result.value)
                                )
                            }

                            is WorkResult.Error -> _searchState.update { it.copy(error = result.error) }
                        }
                    }

                    updateOriginListLoadingState(false)
                }
        }
    }

    private fun collectDestinationKeyword() {
        viewModelScope.launch {
            _searchState.map { it.destinationKeyword }
                .debounce(DEBOUNCE_TIMEOUT)
                .distinctUntilChanged()
                .collectLatest { input ->
                    if (input.isNotBlank() && _searchState.value.selectedDestinationAirport == null) {
                        updateDestinationListLoadingState(true)

                        when (
                            val predictions = airportsRepository.getAirports(input)
                                .filterNotNull()
                                .first()
                        ) {
                            is WorkResult.Success<List<Airport>> -> _searchState.update {
                                it.copy(
                                    destinationSuggestions = it.destinationSuggestions.copy(list = predictions.value)
                                )
                            }

                            is WorkResult.Error -> _searchState.update { it.copy(error = predictions.error) }
                        }
                    }

                    updateDestinationListLoadingState(false)
                }
        }
    }

    private fun updateOriginListLoadingState(isLoading: Boolean) {
        _searchState.update {
            it.copy(originSuggestions = it.originSuggestions.copy(isLoading = isLoading))
        }
    }

    private fun updateDestinationListLoadingState(isLoading: Boolean) {
        _searchState.update {
            it.copy(destinationSuggestions = it.destinationSuggestions.copy(isLoading = isLoading))
        }
    }

    private fun onOriginValueChange(origin: String) {
        _searchState.update {
            it.copy(fromKeyword = origin, selectedOriginAirport = null)
        }

        if (origin.isBlank()) {
            _searchState.update {
                it.copy(originSuggestions = SearchState.ListState())
            }
        }
    }

    private fun onDestinationValueChange(destination: String) {
        _searchState.update {
            it.copy(destinationKeyword = destination, selectedDestinationAirport = null)
        }

        if (destination.isBlank()) {
            _searchState.update {
                it.copy(destinationSuggestions = SearchState.ListState())
            }
        }
    }

    private fun resetOriginSuggestionsList() {
        _searchState.update {
            it.copy(originSuggestions = SearchState.ListState())
        }
    }

    private fun resetDestinationSuggestionsList() {
        _searchState.update {
            it.copy(destinationSuggestions = SearchState.ListState())
        }
    }

    private fun handleOriginAirportSelected(airport: Airport) {
        _searchState.update {
            it.copy(selectedOriginAirport = airport)
        }
        resetOriginSuggestionsList()
    }

    private fun handleDestinationAirportSelected(airport: Airport) {
        _searchState.update {
            it.copy(selectedDestinationAirport = airport)
        }
        resetDestinationSuggestionsList()
    }

    fun handleInteraction(searchScreenCallback: SearchScreenCallback) {
        when (searchScreenCallback) {
            is SearchScreenCallback.OriginValueChange -> onOriginValueChange(searchScreenCallback.origin)
            is SearchScreenCallback.OriginAirportSelected -> handleOriginAirportSelected(
                searchScreenCallback.airport
            )

            is SearchScreenCallback.DestinationValueChange -> onDestinationValueChange(searchScreenCallback.destination)
            is SearchScreenCallback.DestinationAirportSelected -> handleDestinationAirportSelected(
                searchScreenCallback.airport
            )
        }
    }

    companion object {
        private const val DEBOUNCE_TIMEOUT: Long = 500
    }
}

data class SearchState(
    val fromKeyword: String = "",
    val destinationKeyword: String = "",
    val selectedOriginAirport: Airport? = null,
    val selectedDestinationAirport: Airport? = null,
    val originSuggestions: ListState = ListState(),
    val destinationSuggestions: ListState = ListState(),
    val error: UiText? = null
) {
    data class ListState(val list: List<Airport> = emptyList(), val isLoading: Boolean = false)
}

package com.example.flightsapp.ui.screens.search

import com.example.flightsapp.core.model.Airport

sealed interface SearchScreenCallback {
    data class OriginValueChange(val origin: String) : SearchScreenCallback
    data class OriginAirportSelected(val airport: Airport) : SearchScreenCallback
    data class DestinationValueChange(val destination: String) : SearchScreenCallback
    data class DestinationAirportSelected(val airport: Airport) : SearchScreenCallback
}

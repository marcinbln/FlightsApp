package com.example.flightsapp.core.model

data class FlightOffer(
    val price: String,
    val currency: String,
    val itinerary: List<Itinerary>
) {
    data class Itinerary(
        val departureTime: String,
        val arrivalTime: String,
        val departureAirport: String,
        val arrivalAirport: String,
        val toDuration: String,
        val toCarriers: List<String>,
        val segments: List<Segment>
    ) {
        data class Segment(
            val departureTime: String,
            val arrivalTime: String,
            val departureAirport: String,
            val departureCountry: String,
            val arrivalAirport: String,
            val arrivalCountry: String,
            val duration: String,
            val carrier: String,
            val number: String
        )
    }
}

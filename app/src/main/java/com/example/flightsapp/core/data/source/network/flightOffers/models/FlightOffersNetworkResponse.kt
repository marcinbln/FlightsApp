package com.example.flightsapp.core.data.source.network.flightOffers.models

@Suppress("SpellCheckingInspection")
data class FlightOffersNetworkResponse(
    val meta: Meta,
    val data: List<FlightData>,
    val dictionaries: Dictionaries
) {
    data class Meta(
        val count: Int,
        val links: Links
    )

    data class Links(
        val self: String
    )

    data class FlightData(
        val type: String,
        val id: String,
        val source: String,
        val instantTicketingRequired: Boolean,
        val nonHomogeneous: Boolean,
        val oneWay: Boolean,
        val lastTicketingDate: String,
        val lastTicketingDateTime: String,
        val numberOfBookableSeats: Int,
        val itineraries: List<Itinerary>,
        val price: Price,
        val pricingOptions: PricingOptions,
        val validatingAirlineCodes: List<String>,
        val travelerPricing: List<TravelerPricing>
    )

    data class Itinerary(
        val duration: String,
        val segments: List<Segment>
    )

    data class Segment(
        val departure: Location,
        val arrival: Location,
        val carrierCode: String,
        val number: String,
        val aircraft: Aircraft,
        val operating: Operating,
        val duration: String,
        val id: String,
        val numberOfStops: Int,
        val blacklistedInEU: Boolean
    )

    data class Location(
        val iataCode: String,
        val terminal: String? = null,
        val at: String
    )

    data class Aircraft(
        val code: String
    )

    data class Operating(
        val carrierCode: String
    )

    data class Price(
        val currency: String,
        val total: String,
        val base: String,
        val fees: List<Fee>,
        val grandTotal: String
    )

    data class Fee(
        val amount: String,
        val type: String
    )

    data class PricingOptions(
        val fareType: List<String>,
        val includedCheckedBagsOnly: Boolean
    )

    data class TravelerPricing(
        val travelerId: String,
        val fareOption: String,
        val travelerType: String,
        val price: Price,
        val fareDetailsBySegment: List<FareDetails>
    )

    data class FareDetails(
        val segmentId: String,
        val cabin: String,
        val fareBasis: String,
        val `class`: String,
        val includedCheckedBags: CheckedBags
    )

    data class CheckedBags(
        val weight: Int,
        val weightUnit: String
    )

    data class Dictionaries(
        val locations: Map<String, LocationInfo>,
        val aircraft: Map<String, String>,
        val currencies: Map<String, String>,
        val carriers: Map<String, String>
    )

    data class LocationInfo(
        val cityCode: String,
        val countryCode: String
    )
}

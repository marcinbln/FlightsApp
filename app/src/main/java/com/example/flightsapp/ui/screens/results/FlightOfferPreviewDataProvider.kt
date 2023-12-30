package com.example.flightsapp.ui.screens.results

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.flightsapp.core.model.FlightOffer

class FlightOfferPreviewDataProvider : PreviewParameterProvider<FlightOffer> {

    private val segment = FlightOffer.Itinerary.Segment(
        departureTime = "2023-11-11T18:00:00",
        arrivalTime = "2023-11-11T18:00:00",
        departureAirport = "BER",
        departureCountry = "DE",
        arrivalAirport = "MUC",
        arrivalCountry = "DE",
        duration = "PT1H10M",
        carrier = "LH",
        number = "4404"
    )

    private val flightOffer = FlightOffer(
        price = "350.00",
        currency = "EUR",
        itinerary =
        listOf(
            FlightOffer.Itinerary(
                departureTime = "2023-09-25T08:00:00",
                arrivalTime = "2023-09-25T15:00:00",
                departureAirport = "BKK",
                arrivalAirport = "LAX",
                toDuration = "PT8H30M",
                toCarriers = listOf("FD", "AA"),
                segments = listOf(segment, segment)
            ),
            FlightOffer.Itinerary(
                departureTime = "2023-09-28T05:00:00",
                arrivalTime = "2023-09-28T14:00:00",
                departureAirport = "HGG",
                arrivalAirport = "FPC",
                toDuration = "PT4H20M",
                toCarriers = listOf("AS", "DD"),
                segments = listOf(segment, segment)

            )
        )
    )

    override val values = sequenceOf(
        flightOffer,
    )
}

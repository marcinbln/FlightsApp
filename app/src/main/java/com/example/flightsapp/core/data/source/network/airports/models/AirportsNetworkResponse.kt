@file:Suppress("SpellCheckingInspection")

package com.example.flightsapp.core.data.source.network.airports.models

data class AirportsNetworkResponse(
    val meta: Meta,
    val data: List<Location>
) {

    data class Meta(
        val count: Int,
        val links: Links
    )

    data class Links(
        val self: String,
        val next: String,
        val last: String
    )

    data class Location(
        val type: String,
        val subType: String,
        val name: String,
        val detailedName: String,
        val id: String,
        val self: Self,
        val timeZoneOffset: String,
        val iataCode: String,
        val geoCode: GeoCode,
        val address: Address,
        val analytics: Analytics
    )

    data class Self(
        val href: String,
        val methods: List<String>
    )

    data class GeoCode(
        val latitude: Double,
        val longitude: Double
    )

    data class Address(
        val cityName: String,
        val cityCode: String,
        val countryName: String,
        val countryCode: String,
        val stateCode: String,
        val regionCode: String
    )

    data class Analytics(
        val travelers: Travelers
    )

    data class Travelers(
        val score: Int
    )
}

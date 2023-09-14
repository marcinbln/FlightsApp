package com.example.flightsapp.core.data.source.network.airports

import com.example.flightsapp.core.common.UiText
import com.example.flightsapp.core.common.WorkResult
import com.example.flightsapp.core.common.parseError
import com.example.flightsapp.core.data.source.network.flightOffers.models.ApiErrorResponse
import com.example.flightsapp.core.model.Airport
import com.example.flightsapp.core.network.AirportsService
import javax.inject.Inject

class AirportsRemoteDataSource @Inject constructor(
    private val airportsApi: AirportsService
) : AirportsDataSource {

    override suspend fun getAirports(keyword: String): WorkResult<List<Airport>> {
        val response = airportsApi.getAirports(keyword = keyword)

        with(response) {
            if (isSuccessful) {
                val airportsResponse: List<Airport> = response.body()?.data
                    ?.filter { it.address.cityName.contains(keyword, true) }
                    ?.map {
                        Airport(
                            iata = it.iataCode,
                            cityName = it.address.cityName,
                            countryName = it.address.countryName,
                            countryCode = it.address.countryCode
                        )
                    } ?: emptyList()

                return WorkResult.Success(airportsResponse)
            } else {
                val apiErrorResponse: ApiErrorResponse? = response.errorBody()
                    .parseError()
                val error = apiErrorResponse?.errors
                    ?.first()
                    ?.let {
                        "${it.title}: ${it.detail}"
                    } ?: ""

                return WorkResult.Error(UiText.DynamicString(error))
            }
        }
    }
}

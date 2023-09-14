package com.example.flightsapp.core.data.repositories.flightOffers

import android.util.Log
import com.example.flightsapp.core.common.UiText
import com.example.flightsapp.core.common.WorkResult
import com.example.flightsapp.core.common.parseError
import com.example.flightsapp.core.data.source.network.flightOffers.FlightOffersService
import com.example.flightsapp.core.data.source.network.flightOffers.models.FlightOffersNetworkResponse
import com.example.flightsapp.core.model.SearchQuery
import com.example.flightsapp.core.network.TokenAuthenticator
import com.example.flightsapp.core.network.TokenInterceptor
import com.example.flightsapp.core.model.FlightOffer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

const val BASE_URL = "https://test.api.amadeus.com"

class FlightOffersRepositoryImpl @Inject constructor(
    tokenInterceptor: TokenInterceptor,
    authenticator: TokenAuthenticator
) : FlightOffersRepository {

    private val flightsNetworkApi = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient
                .Builder()
                .authenticator(authenticator)
                .addInterceptor(tokenInterceptor)
                .addInterceptor(
                    HttpLoggingInterceptor().also {
                        it.level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .build(),
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FlightOffersService::class.java)

    override suspend fun getFlightOffers(flightQuery: SearchQuery): WorkResult<List<FlightOffer>> {
        return try {
            val response = flightsNetworkApi.getFlightOffers(
                originLocationCode = flightQuery.originLocationCode,
                destinationLocationCode = flightQuery.destinationLocationCode,
                departureDate = flightQuery.departureDate,
                returnDate = flightQuery.returnDate,
                adults = 1,
                max = 20
            )

            when {
                response.isSuccessful -> {
                    val responseBody = response.body()
                    val flightOffers: List<FlightOffer> = responseBody?.data?.map { flightData ->
                        mapFlightDataToFlightOffer(flightData, responseBody)
                    } ?: emptyList()
                    return WorkResult.Success(flightOffers)
                }

                else -> handleErrorResponse(response)
            }
        } catch (e: HttpException) {
            handleException(e)
        } catch (
            @Suppress("TooGenericExceptionCaught")
            e: Throwable
        ) {
            Log.e("FlightOffersRepositoryImpl", "Exception occurred: ${e.message}")
            handleException(e)
        }
    }

    private fun mapFlightDataToFlightOffer(
        flightData: FlightOffersNetworkResponse.FlightData,
        responseBody: FlightOffersNetworkResponse
    ): FlightOffer {
        return FlightOffer(
            price = flightData.price.total,
            currency = flightData.price.currency,
            itinerary = flightData.itineraries.map { itinerary ->
                FlightOffer.Itinerary(
                    departureTime = itinerary.segments.first().departure.at,
                    arrivalTime = itinerary.segments.last().arrival.at,
                    departureAirport = itinerary.segments.first().departure.iataCode,
                    arrivalAirport = itinerary.segments.last().arrival.iataCode,
                    toDuration = itinerary.duration,
                    toCarriers = itinerary.segments
                        .map { segment -> segment.carrierCode }
                        .distinct(),
                    segments = itinerary.segments.map {
                        FlightOffer.Itinerary.Segment(
                            departureTime = it.departure.at,
                            arrivalTime = it.arrival.at,
                            departureAirport = it.departure.iataCode,
                            departureCountry = responseBody.dictionaries.locations[it.departure.iataCode]?.countryCode
                                ?: "",
                            arrivalAirport = it.arrival.iataCode,
                            arrivalCountry = responseBody.dictionaries.locations[it.departure.iataCode]?.countryCode
                                ?: "",
                            duration = it.duration,
                            carrier = it.carrierCode,
                            number = it.number
                        )
                    }
                )
            }
        )
    }

    private fun handleErrorResponse(response: Response<FlightOffersNetworkResponse>): WorkResult<List<FlightOffer>> {
        val error = response.errorBody()
            ?.parseError()?.errors?.first()?.detail ?: "Something went wrong"
        return WorkResult.Error(UiText.DynamicString(error))
    }

    private fun handleException(e: Throwable): WorkResult<List<FlightOffer>> {
        return WorkResult.Error(UiText.DynamicString(e.message.toString()))
    }


}

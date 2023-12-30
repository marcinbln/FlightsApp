package com.example.flightsapp.ui.screens.results.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R
import com.example.flightsapp.core.common.toDurationFormatted
import com.example.flightsapp.core.common.toSeparateHourAndMin
import com.example.flightsapp.core.model.FlightOffer
import com.example.flightsapp.ui.screens.results.FlightOfferPreviewDataProvider
import com.example.flightsapp.ui.theme.Blue700
import com.example.flightsapp.ui.theme.FlightsAppTheme

@Composable
internal fun ResultCardReturn(
    modifier: Modifier = Modifier,
    item: FlightOffer,
    isRoundTrip: Boolean,
    onClick: (FlightOffer) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { onClick(item) }
    ) {
        Column(
            modifier = modifier.padding(24.dp)
        ) {
            Itinerary(item = item.itinerary[0])

            Spacer(modifier = modifier.height(10.dp))

            if (isRoundTrip) Itinerary(item = item.itinerary[1])

            Spacer(modifier = modifier.height(10.dp))

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.align(CenterVertically)) {
                    Icon(
                        modifier = modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_airlines),
                        tint = Blue700,
                        contentDescription = null
                    )

                    val carriers: List<String> = item.itinerary[0].toCarriers + if (isRoundTrip) {
                        item.itinerary[1].toCarriers
                    } else {
                        emptyList()
                    }.distinct()

                    Text(
                        modifier = modifier.align(CenterVertically),
                        text = carriers
                            .distinct()
                            .joinToString(),
                    )

                    Spacer(modifier = modifier.width(2.dp))
                }

                Row(verticalAlignment = CenterVertically) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.ic_price),
                        tint = MaterialTheme.colorScheme.surfaceTint,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${item.currency} ${item.price}")
                }
            }
        }
    }
}

@Composable
internal fun Itinerary(
    modifier: Modifier = Modifier,
    item: FlightOffer.Itinerary
) {
    val stops = item.segments.size - 1

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        TimeAirportColumn(
            time = item.departureTime,
            airportCode = item.departureAirport
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Blue700.copy(0.3f), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .background(Blue700, shape = CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                DashedLine(Modifier.weight(1f))

                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.plane_bird_view),
                    contentDescription = null
                )

                DashedLine(Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(Blue700),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = item.toDuration
                    .toSeparateHourAndMin()
                    .toDurationFormatted(),
                color = Blue700,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = if (stops == 0) {
                    "Direct"
                } else {
                    pluralStringResource(
                        R.plurals.stops_format,
                        stops,
                        stops
                    )
                },
                color = if (stops == 0) Color.Green else Color.Red,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }

        TimeAirportColumn(
            time = item.arrivalTime,
            airportCode = item.arrivalAirport
        )
    }
}

@Preview(showBackground = true, device = "spec:width=1440px,height=2960px,dpi=570")
@Composable
private fun ResultCardPreview(
    @PreviewParameter(FlightOfferPreviewDataProvider::class) flightOffer: FlightOffer
) {
    FlightsAppTheme {
        ResultCardReturn(item = flightOffer, isRoundTrip = false, onClick = {})
    }
}

package com.example.flightsapp.ui.screens.results.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R
import com.example.flightsapp.core.common.toDurationFormatted
import com.example.flightsapp.core.common.toFormattedTime
import com.example.flightsapp.core.common.toSeparateHourAndMin
import com.example.flightsapp.core.model.FlightOffer
import com.example.flightsapp.core.common.ui.FlightOfferPreviewDataProvider
import com.example.flightsapp.ui.theme.Blue700
import com.example.flightsapp.ui.theme.FlightsAppTheme

@Composable
internal fun ExpandableContent(
    modifier: Modifier = Modifier,
    segment: FlightOffer.Itinerary.Segment
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = modifier.size(32.dp),
                painter = painterResource(id = R.drawable.ic_airlines),
                tint = Blue700,
                contentDescription = null
            )

            Text(
                text = buildString {
                    append("${segment.carrier} ${segment.number}  · ")
                    append("${segment.departureAirport} (${segment.departureCountry}) to ")
                    append("${segment.arrivalAirport} (${segment.arrivalCountry})")
                }
            )
        }

        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Column(
                modifier = modifier.padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(id = R.drawable.ic_trip_origin),
                    tint = MaterialTheme.colorScheme.surfaceTint,
                    contentDescription = null
                )

                VerticalDivider(modifier = Modifier.weight(1f))

                Icon(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(id = R.drawable.ic_trip_origin),
                    tint = MaterialTheme.colorScheme.surfaceTint,
                    contentDescription = null
                )
            }

            Column {
                Text(text = segment.departureTime.toFormattedTime("HH:mm, dd/MM/yyyy"))
                Spacer(modifier = modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(id = R.drawable.ic_watch),
                        tint = MaterialTheme.colorScheme.surfaceTint,
                        contentDescription = null
                    )
                    Spacer(modifier = modifier.width(4.dp))
                    Text(
                        text = segment.duration
                            .toSeparateHourAndMin()
                            .toDurationFormatted()
                    )
                }
                Spacer(modifier = modifier.height(10.dp))

                Text(text = segment.arrivalTime.toFormattedTime("HH:mm, dd/MM/yyyy"))
            }
        }
        Spacer(modifier = modifier.height(8.dp))
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF2BB6EC,
    device = "spec:width=1440px,height=2960px,dpi=570"
)
@Composable
private fun ResultCardPreview(
    @PreviewParameter(FlightOfferPreviewDataProvider::class) flightOffer: FlightOffer
) {
    FlightsAppTheme {
        ExpandableContent(
            segment = flightOffer.itinerary.first().segments.first()
        )
    }
}

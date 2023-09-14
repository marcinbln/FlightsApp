package com.example.flightsapp.ui.screens.results.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R
import com.example.flightsapp.core.model.FlightOffer

@Composable
internal fun PriceRow(
    modifier: Modifier = Modifier,
    flightOffer: FlightOffer
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = modifier.size(14.dp),
            painter = painterResource(id = R.drawable.ic_price),
            tint = MaterialTheme.colorScheme.surfaceTint,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "${flightOffer.currency} ${flightOffer.price}")
    }
}

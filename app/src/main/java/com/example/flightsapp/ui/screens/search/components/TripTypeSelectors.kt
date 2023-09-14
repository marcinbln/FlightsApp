package com.example.flightsapp.ui.screens.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flightsapp.R
import com.example.flightsapp.ui.screens.search.conditional
import com.example.flightsapp.ui.theme.Blue700

private const val UNDER_LINE_LENGTH = 0.75f

@Composable
internal fun TripTypeSelectors(
    modifier: Modifier = Modifier,
    isRoundTrip: MutableState<Boolean>
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        TextButton(onClick = { isRoundTrip.value = true }) {
            Text(
                text = stringResource(R.string.search_round_trip_option),
                modifier = modifier.conditional(
                    condition = isRoundTrip.value,
                    ifTrue = { drawBehind { drawUnderline() } },
                ),
                fontWeight = if (isRoundTrip.value) FontWeight.Bold else FontWeight.Normal,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        TextButton(onClick = { isRoundTrip.value = false }) {
            Text(
                modifier = modifier.conditional(
                    condition = !isRoundTrip.value,
                    ifTrue = { drawBehind { drawUnderline() } },
                ),
                text = stringResource(R.string.search_one_way_option),
                fontWeight = if (!isRoundTrip.value) FontWeight.Bold else FontWeight.Normal,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

private fun DrawScope.drawUnderline() {
    val strokeWidthPx = 1.dp.toPx()
    val verticalOffset = size.height + 3.sp.toPx()
    drawLine(
        color = Blue700,
        strokeWidth = strokeWidthPx,
        start = Offset(0f, verticalOffset),
        end = Offset(UNDER_LINE_LENGTH * size.width, verticalOffset)
    )
}

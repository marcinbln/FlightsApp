package com.example.flightsapp.ui.screens.search.components

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flightsapp.ui.screens.search.conditional
import com.example.flightsapp.ui.theme.Blue700

private const val UNDER_LINE_LENGTH = 0.75f

@Composable
internal fun TripTypeSelector(
    isRoundTrip: Boolean,
    onClick: () -> Unit,
    @StringRes text: Int
) {
    TextButton(onClick = onClick) {
        Text(
            text = stringResource(text),
            modifier = Modifier.conditional(
                condition = isRoundTrip,
                ifTrue = { drawBehind { drawUnderline() } },
            ),
            fontWeight = if (isRoundTrip) FontWeight.Bold else FontWeight.Normal,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
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

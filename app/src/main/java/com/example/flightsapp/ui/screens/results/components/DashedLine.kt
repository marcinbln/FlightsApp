package com.example.flightsapp.ui.screens.results.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp

@Composable
internal fun DashedLine(modifier: Modifier = Modifier) {
    val pathEffect = PathEffect.dashPathEffect(
        floatArrayOf(10f, 10f),
        0f
    )
    Canvas(
        modifier
            .height(1.dp)
    ) {
        drawLine(
            color = Color.Gray,
            start = Offset(
                0f,
                0f
            ),
            end = Offset(
                size.width,
                0f
            ),
            pathEffect = pathEffect
        )
    }
}

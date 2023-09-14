package com.example.flightsapp.ui.screens.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun RowScope.AnimatedSpacer(
    isRoundTrip: Boolean
) {
    AnimatedVisibility(
        visible = isRoundTrip,
        exit = slideOutHorizontally(
            tween(durationMillis = 200, easing = FastOutLinearInEasing),
            targetOffsetX = { it / 2 }
        ),
    ) {
        Spacer(
            modifier = Modifier
                .animateContentSize()
                .width(25.dp)
        )
    }
}

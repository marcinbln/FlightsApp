package com.example.flightsapp.ui.screens.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.flightsapp.R
import com.example.flightsapp.core.common.toFormattedDate
import com.example.flightsapp.ui.theme.Blue700
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
internal fun RowScope.AnimatedReturnDateTextField(
    isRoundTrip: Boolean,
    returnDate: Long,
    onClick: () -> Unit
) {
    val interactionSource = remember {
        object : MutableInteractionSource {
            override val interactions = MutableSharedFlow<Interaction>(
                extraBufferCapacity = 16,
                onBufferOverflow = BufferOverflow.DROP_OLDEST,
            )

            override suspend fun emit(interaction: Interaction) {
                if (interaction is PressInteraction.Release) {
                    onClick()
                }

                interactions.emit(interaction)
            }

            override fun tryEmit(interaction: Interaction): Boolean {
                return interactions.tryEmit(interaction)
            }
        }
    }

    AnimatedVisibility(
        modifier = Modifier.weight(1f),
        visible = isRoundTrip,
        enter = slideInHorizontally(initialOffsetX = { it / 2 }),
        exit = slideOutHorizontally(
            tween(
                durationMillis = 200,
                easing = FastOutLinearInEasing
            ),
            targetOffsetX = { it / 2 }
        ),
    ) {
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Blue700),
            readOnly = true,
            singleLine = true,
            onValueChange = {},
            interactionSource = interactionSource,
            value = returnDate.toFormattedDate(),
            label = { Text(text = stringResource(R.string.search_return_date_label)) },
            textStyle = MaterialTheme.typography.bodyLarge
        )
    }
}

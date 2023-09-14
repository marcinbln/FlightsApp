package com.example.flightsapp.ui.screens.search.components

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
internal fun RowScope.FromDateTextField(
    departureDate: Long,
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

    OutlinedTextField(
        modifier = Modifier.weight(1f),
        interactionSource = interactionSource,
        colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Blue700),
        readOnly = true,
        singleLine = true,
        value = departureDate.toFormattedDate(),
        onValueChange = {},
        label = { Text(text = stringResource(R.string.search_depart_date_label)) },
        textStyle = MaterialTheme.typography.bodyLarge
    )
}

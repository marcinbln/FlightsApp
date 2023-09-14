package com.example.flightsapp.ui.screens.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R
import com.example.flightsapp.ui.theme.Blue700

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun DatesFields(
    modifier: Modifier,
    departureDate: MutableState<String>,
    isRoundTrip: MutableState<Boolean>,
    returnDate: MutableState<String>
) {
    Row(
        modifier = modifier
            .animateContentSize()
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = modifier.weight(1f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Blue700
            ),
            singleLine = true,
            value = departureDate.value,
            onValueChange = { departureDate.value = it },
            label = { Text(text = stringResource(R.string.search_depart_date_label)) },
            textStyle = MaterialTheme.typography.bodyLarge
        )

        AnimatedVisibility(
            visible = isRoundTrip.value,
            exit = slideOutHorizontally(
                tween(
                    durationMillis = 200,
                    easing = FastOutLinearInEasing
                ),
                targetOffsetX = { it / 2 }
            ),
        ) {
            Spacer(
                modifier = modifier
                    .animateContentSize()
                    .width(25.dp)
            )
        }

        AnimatedVisibility(
            modifier = modifier.weight(1f),
            visible = isRoundTrip.value,
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
                colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Blue700),
                singleLine = true,
                value = returnDate.value,
                onValueChange = { returnDate.value = it },
                label = { Text(text = stringResource(R.string.search_return_date_label)) },
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

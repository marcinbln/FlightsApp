package com.example.flightsapp.ui.screens.search.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flightsapp.core.common.toFormattedDate

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun DateRangePicker(dateRangePickerState: DateRangePickerState) {
    androidx.compose.material3.DateRangePicker(
        modifier = Modifier.height(height = 500.dp), // Dialog's buttons are not appearing without setting the height
        state = dateRangePickerState,
        title = {
            Text(
                modifier = Modifier.padding(start = 24.dp, end = 12.dp, top = 16.dp),
                text = "Pick departure and return dates"
            )
        },
        headline = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = dateRangePickerState.selectedStartDateMillis?.toFormattedDate("dd/MM/yyyy")
                        ?: "Start date"
                )

                Text(text = " — ")

                Text(
                    text = dateRangePickerState.selectedEndDateMillis?.toFormattedDate("dd/MM/yyyy")
                        ?: "End date"
                )
            }
        },
    )
}

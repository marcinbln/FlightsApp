package com.example.flightsapp.ui.screens.search.components

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableLongState

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun RangeDatePickerDialog(
    showDateRangePicker: (Boolean) -> Unit,
    dateRangePickerState: DateRangePickerState,
    departureDate: MutableLongState,
    returnDate: MutableLongState,
) {
    DatePickerDialog(onDismissRequest = {
        showDateRangePicker(false)
    }, confirmButton = {
        TextButton(onClick = {
            showDateRangePicker(false)
            dateRangePickerState.selectedStartDateMillis?.let {
                departureDate.longValue = it
            }
            dateRangePickerState.selectedEndDateMillis?.let { returnDate.longValue = it }
        }) {
            Text(text = "Confirm")
        }
    }, dismissButton = {
        TextButton(onClick = {
            showDateRangePicker(false)
        }) {
            Text(text = "Cancel")
        }
    }, content = {
        DateRangePicker(dateRangePickerState = dateRangePickerState)
    })
}

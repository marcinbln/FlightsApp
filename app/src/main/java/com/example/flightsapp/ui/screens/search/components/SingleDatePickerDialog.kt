package com.example.flightsapp.ui.screens.search.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableLongState

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun SingleDatePickerDialog(
    showDateRangePicker: (Boolean) -> Unit,
    datePickerState: DatePickerState,
    departureDate: MutableLongState,
) {
    DatePickerDialog(onDismissRequest = {
        showDateRangePicker(false)
    }, confirmButton = {
        TextButton(onClick = {
            showDateRangePicker(false)
            datePickerState.selectedDateMillis?.let {
                departureDate.longValue = it
            }
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
        DatePicker(state = datePickerState)
    })
}

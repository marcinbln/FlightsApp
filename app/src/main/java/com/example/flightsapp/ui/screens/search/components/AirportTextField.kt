package com.example.flightsapp.ui.screens.search.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsapp.R
import com.example.flightsapp.ui.theme.Blue700

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun AirportTextField(
    fromAirport: String,
    label: Int,
    icPlaneArriving: Int,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Blue700),
        value = fromAirport,
        singleLine = true,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(label)) },
        textStyle = MaterialTheme.typography.titleLarge,
        trailingIcon = {
            Icon(
                painter = painterResource(id = icPlaneArriving),
                contentDescription = null
            )
        }
    )
}

@Preview
@Composable
private fun AirportTextFieldPreview() {
    AirportTextField(
        fromAirport = "Warsaw",
        label = R.string.search_from_label,
        icPlaneArriving = R.drawable.ic_plane_departing,
        onValueChange = {}
    )
}

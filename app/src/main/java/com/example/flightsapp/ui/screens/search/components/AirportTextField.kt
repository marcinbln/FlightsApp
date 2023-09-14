package com.example.flightsapp.ui.screens.search.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R
import com.example.flightsapp.core.model.Airport
import com.example.flightsapp.ui.screens.search.SearchState
import com.example.flightsapp.ui.theme.Blue700

@Composable
internal fun AirportTextField(
    modifier: Modifier = Modifier,
    fromAirport: String,
    label: Int,
    icPlaneArriving: Int,
    optionsList: SearchState.ListState,
    onValueChange: (String) -> Unit,
    onValueSelected: (Airport) -> Unit
) {
    val hasFocus = remember { mutableStateOf(false) }
    val showPredictions = remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Column {
        if (shouldShowPredictions(hasFocus, fromAirport, showPredictions, optionsList)) {
            LazyColumn(
                modifier = modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .heightIn(max = TextFieldDefaults.MinHeight * 4)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentPadding = PaddingValues(6.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(optionsList.list) { item ->
                    val itemText = "${item.cityName} (${item.countryName})"
                    Text(
                        modifier = modifier
                            .fillMaxWidth()
                            .clickable {
                                onValueChange("${item.cityName} (${item.countryCode})")
                                onValueSelected(item)
                                showPredictions.value = false
                                focusManager.clearFocus()
                            },
                        text = itemText
                    )
                }
            }
        }
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                hasFocus.value = it.hasFocus
            },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Blue700,
        ),
        value = fromAirport,
        singleLine = true,
        onValueChange = { input ->
            showPredictions.value = true
            onValueChange(input)
        },
        label = { Text(text = stringResource(label)) },
        placeholder = { Text(text = "Type in city name...") },
        textStyle = MaterialTheme.typography.titleLarge,
        trailingIcon = {
            Row(Modifier.padding(end = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(20.dp)) {
                    if (optionsList.isLoading) {
                        CircularProgressIndicator(Modifier.size(20.dp))
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    painter = painterResource(id = icPlaneArriving),
                    contentDescription = null
                )
            }
        }
    )
}

private fun shouldShowPredictions(
    hasFocus: MutableState<Boolean>,
    fromAirport: String,
    showPredictions: MutableState<Boolean>,
    optionsList: SearchState.ListState
) = hasFocus.value && fromAirport.isNotBlank() && showPredictions.value && optionsList.list.isNotEmpty()

@Preview
@Composable
private fun AirportTextFieldPreview() {
    val fromAirport = remember { mutableStateOf("") }

    AirportTextField(
        fromAirport = fromAirport.value,
        label = R.string.search_from_label,
        icPlaneArriving = R.drawable.ic_plane_departing,
        onValueChange = { fromAirport.value = it },
        optionsList = SearchState.ListState(
            listOf(
                Airport(
                    "ATL",
                    "Atlanta",
                    "United States of America",
                    countryCode = "UK"
                ),
                Airport(
                    "ATL",
                    "Atlanta",
                    "United States of America",
                    countryCode = "UK"
                ),
                Airport(
                    "ATL",
                    "Atlanta",
                    "United States of America",
                    countryCode = "UK"
                ),
                Airport(
                    "ATL",
                    "Atlanta",
                    "United States of America",
                    countryCode = "UK"
                ),
                Airport(
                    "ATL",
                    "Atlanta",
                    "United States of America",
                    countryCode = "UK"
                ),
            )
        ),
        onValueSelected = {}
    )
}

package com.example.flightsapp.ui.screens.results

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flightsapp.R
import com.example.flightsapp.core.common.DevicePreviews
import com.example.flightsapp.core.common.UiText
import com.example.flightsapp.core.common.showToast
import com.example.flightsapp.core.common.ui.FlightOfferPreviewDataProvider
import com.example.flightsapp.core.common.ui.LoadingIndicator
import com.example.flightsapp.core.model.FlightOffer
import com.example.flightsapp.ui.screens.results.components.ResultCardReturn
import com.example.flightsapp.ui.screens.results.components.ResultCardReturnExpanded

@Composable
fun ResultsRoute(
    resultsViewModel: ResultsViewModel = hiltViewModel()
) {
    val resultsState = resultsViewModel.resultsState.collectAsStateWithLifecycle()
    ResultsScreen(resultsState = resultsState.value)
}

@Composable
internal fun ResultsScreen(
    modifier: Modifier = Modifier,
    resultsState: ResultsState
) {
    val context = LocalContext.current
    val expanded = remember { mutableStateOf(false) }
    val flightOffer = remember { mutableStateOf<FlightOffer?>(null) }
    val list: List<FlightOffer> = resultsState.resultsList

    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp)
    ) {
        Header(modifier, resultsState)

        Spacer(modifier = modifier.height(22.dp))

        if (resultsState.isLoading) {
            LoadingIndicator()
        } else {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (list.isNotEmpty()) {
                    LazyColumn(
                        contentPadding = PaddingValues(top = 12.dp, bottom = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(list) { item ->
                            ResultCardReturn(
                                item = item,
                                isRoundTrip = resultsState.isRoundTrip,
                                onClick = {
                                    flightOffer.value = it
                                    expanded.value = !expanded.value
                                }
                            )
                        }
                    }
                } else {
                    Box(Modifier.fillMaxSize(), contentAlignment = Center) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                modifier = Modifier.size(50.dp),
                                painter = painterResource(id = R.drawable.no_results),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "No flights found",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        androidx.compose.animation.AnimatedVisibility(
            modifier = modifier.align(Center),
            visible = expanded.value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            flightOffer.value?.let {
                ResultCardReturnExpanded(
                    modifier = modifier,
                    flightOffer = it,
                    resultsState = resultsState,
                    onClose = { expanded.value = false }
                )
            }
        }
    }

    when (resultsState.error) {
        is UiText.DynamicString -> LaunchedEffect(Unit) { context.showToast(resultsState.error.value) }
        is UiText.ResourceString -> {
            val error = stringResource(id = resultsState.error.resId)
            LaunchedEffect(Unit) { context.showToast(error) }
        }

        null -> Unit
    }
}

@Composable
private fun Header(
    modifier: Modifier,
    resultsState: ResultsState
) {
    Column(modifier = modifier.padding(start = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = resultsState.fromAirport,
                style = MaterialTheme.typography.headlineMedium,
            )

            Spacer(modifier = modifier.width(8.dp))

            Icon(
                modifier = modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_swap),
                contentDescription = null
            )

            Spacer(modifier = modifier.width(8.dp))

            Text(
                text = resultsState.toAirport,
                style = MaterialTheme.typography.headlineMedium,
            )
        }
        Text(
            text = resultsState.fromDate + if (resultsState.isRoundTrip) {
                " - " + resultsState.toDate
            } else {
                ""
            }
        )
    }
}

@DevicePreviews
@Composable
private fun ResultsPreview(
    @PreviewParameter(FlightOfferPreviewDataProvider::class) flightOffer: FlightOffer
) {
    ResultsScreen(
        resultsState = ResultsState(
            isLoading = false,
            fromAirport = "Ber",
            toAirport = "Lon",
            resultsList = listOf(flightOffer, flightOffer)
        )
    )
}

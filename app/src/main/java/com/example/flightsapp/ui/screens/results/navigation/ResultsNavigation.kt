package com.example.flightsapp.ui.screens.results.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.flightsapp.navigation.Screen
import com.example.flightsapp.ui.screens.results.ResultsRoute

internal class ResultsArgs(
    val from: String,
    val to: String,
    val departureDate: Long,
    val returnDate: Long,
    val isRoundTrip: Boolean
) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        checkNotNull(savedStateHandle[Argument.FromArg.name]),
        checkNotNull(savedStateHandle[Argument.ToArg.name]),
        checkNotNull(savedStateHandle[Argument.DepartureDate.name]),
        checkNotNull(savedStateHandle[Argument.ReturnDate.name]),
        checkNotNull(savedStateHandle[Argument.IsRoundTrip.name])
    )
}

fun NavController.navigateToResults(
    from: String,
    to: String,
    departureDate: Long,
    returnDate: Long,
    isRoundTrip: Boolean
) {
    this.navigate("${Screen.Results.baseRoute}/$from/$to/$departureDate/$returnDate/$isRoundTrip")
}

fun NavGraphBuilder.resultsScreen() {
    composable(
        route = Screen.Results.route,
        arguments = listOf(
            navArgument(Argument.FromArg.name) { type = NavType.StringType },
            navArgument(Argument.ToArg.name) { type = NavType.StringType },
            navArgument(Argument.DepartureDate.name) { type = NavType.LongType },
            navArgument(Argument.ReturnDate.name) { type = NavType.LongType },
            navArgument(Argument.IsRoundTrip.name) { type = NavType.BoolType }
        )
    ) {
        ResultsRoute()
    }
}

enum class Argument {
    FromArg, ToArg, DepartureDate, ReturnDate, IsRoundTrip
}

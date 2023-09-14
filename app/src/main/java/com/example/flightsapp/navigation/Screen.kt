package com.example.flightsapp.navigation

import com.example.flightsapp.R
import com.example.flightsapp.ui.screens.results.navigation.Argument

enum class Screen(
    val baseRoute: String,
    val backgroundDrawable: Int,
    val isTopLevel: Boolean = false,
    private val arguments: List<Argument> = emptyList()
) {
    LOADING(
        baseRoute = "loading_route",
        backgroundDrawable = R.drawable.app_background_1,
        isTopLevel = true
    ),
    SPLASH(
        baseRoute = "splash_route",
        backgroundDrawable = R.drawable.app_background_1,
        isTopLevel = true
    ),
    SIGN_IN(
        baseRoute = "signIn_route",
        backgroundDrawable = R.drawable.app_background_2,
    ),
    SIGN_UP(
        baseRoute = "signUp_route",
        backgroundDrawable = R.drawable.app_background_2,
    ),
    SEARCH(
        baseRoute = "search_route",
        backgroundDrawable = R.drawable.app_background_3,
        isTopLevel = true
    ),
    Settings(
        baseRoute = "settings_route",
        backgroundDrawable = R.drawable.app_background_2,
    ),
    Results(
        baseRoute = "results_route",
        backgroundDrawable = R.drawable.app_background_2,
        arguments = listOf(
            Argument.FromArg,
            Argument.ToArg,
            Argument.DepartureDate,
            Argument.ReturnDate,
            Argument.IsRoundTrip
        )
    );

    val route: String
        get() {
            return if (arguments.isNotEmpty()) {
                val routeBuilder = StringBuilder(baseRoute)
                arguments.forEach { routeBuilder.append("/{${it.name}}") }
                routeBuilder.toString()
            } else {
                baseRoute
            }
        }
}

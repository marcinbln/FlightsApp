package com.example.flightsapp.navigation

import com.example.flightsapp.R

enum class Screen(
    val route: String,
    val backgroundDrawable: Int,
    val isTopLevel: Boolean = false,
    val isTopAppBarTransparent: Boolean
) {
    LOADING(
        route = "loading_route",
        backgroundDrawable = R.drawable.app_background_1,
        isTopLevel = true,
        isTopAppBarTransparent = true
    ),
    SPLASH(
        route = "splash_route",
        backgroundDrawable = R.drawable.app_background_1,
        isTopLevel = true,
        isTopAppBarTransparent = true
    ),
    SIGN_IN(
        route = "signIn_route",
        backgroundDrawable = R.drawable.app_background_2,
        isTopAppBarTransparent = true
    ),
    SIGN_UP(
        route = "signUp_route",
        backgroundDrawable = R.drawable.app_background_2,
        isTopAppBarTransparent = true
    ),
    SEARCH(
        route = "search_route",
        backgroundDrawable = R.drawable.app_background_3,
        isTopLevel = true,
        isTopAppBarTransparent = true
    )
}

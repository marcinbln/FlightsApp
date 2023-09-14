package com.example.flightsapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flightsapp.core.common.clearBackStack
import com.example.flightsapp.ui.FlightsAppState
import com.example.flightsapp.ui.screens.loadingScreen.LoadingRoute
import com.example.flightsapp.ui.screens.results.navigation.navigateToResults
import com.example.flightsapp.ui.screens.results.navigation.resultsScreen
import com.example.flightsapp.ui.screens.search.SearchRoute
import com.example.flightsapp.ui.screens.settings.SettingsRoute
import com.example.flightsapp.ui.screens.signIn.SignInRoute
import com.example.flightsapp.ui.screens.signUp.SignUpRoute
import com.example.flightsapp.ui.screens.splash.SplashRoute

@Composable
fun FlightsNavHost(
    modifier: Modifier = Modifier,
    flightsAppState: FlightsAppState
) {
    val navController = flightsAppState.navController

    NavHost(
        modifier = modifier,
        navController = flightsAppState.navController,
        startDestination = Screen.LOADING.route,
        enterTransition = { fadeIn(animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(400)) },
        popEnterTransition = { fadeIn(animationSpec = tween(500)) },
        popExitTransition = { fadeOut(animationSpec = tween(400)) }
    ) {
        composable(Screen.LOADING.route) {
            LoadingRoute(onSignedOut = {
                navController.navigate(Screen.SPLASH.route) {
                    clearBackStack(navController)
                }
            }, onSignedIn = {
                navController.navigate(Screen.SEARCH.route) {
                    clearBackStack(navController)
                }
            })
        }

        composable(Screen.SPLASH.route) {
            SplashRoute(
                onSignInClicked = { navController.navigate(Screen.SIGN_IN.route) },
                onSignUpClicked = { navController.navigate(Screen.SIGN_UP.route) }
            )
        }

        composable(Screen.SIGN_IN.route) {
            SignInRoute {
                navController.navigate(Screen.SEARCH.route) {
                    clearBackStack(navController)
                }
            }
        }

        composable(Screen.SIGN_UP.route) {
            SignUpRoute(onAccountCreated = {
                navController.navigate(Screen.SEARCH.route) {
                    clearBackStack(navController)
                }
            })
        }

        composable(Screen.SEARCH.route) {
            SearchRoute(
                onSettingsClicked = { navController.navigate(Screen.Settings.route) },
                onSearchClicked = { from, to, departDate, returnDate, isRoundTrip ->
                    navController.navigateToResults(
                        from = from,
                        to = to,
                        departureDate = departDate,
                        returnDate = returnDate,
                        isRoundTrip = isRoundTrip
                    )
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsRoute()
        }

        resultsScreen()
    }
}

package com.example.flightsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flightsapp.ui.screens.loadingScreen.LoadingRoute
import com.example.flightsapp.ui.screens.search.SearchRoute
import com.example.flightsapp.ui.screens.signIn.SignInRoute
import com.example.flightsapp.ui.screens.signUp.SignUpRoute
import com.example.flightsapp.ui.screens.splash.SplashRoute

@Composable
fun FlightsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.LOADING.route
    ) {
        composable(Screen.LOADING.route) {
            LoadingRoute(
                onSignedOut = {
                    navController.navigate(Screen.SPLASH.route) {
                        popUpToTop(
                            navController
                        )
                    }
                },
                onSignedIn = {
                    navController.navigate(Screen.SEARCH.route) {
                        popUpToTop(
                            navController
                        )
                    }
                }
            )
        }

        composable(Screen.SPLASH.route) {
            SplashRoute(
                onSignInClicked = { navController.navigate(Screen.SIGN_IN.route) },
                onSignUpClicked = { navController.navigate(Screen.SIGN_UP.route) }
            )
        }

        composable(Screen.SIGN_IN.route) {
            SignInRoute(
                onLoggedIn = {
                    navController.navigate(Screen.SEARCH.route) {
                        popUpToTop(
                            navController
                        )
                    }
                }
            )
        }

        composable(Screen.SIGN_UP.route) {
            SignUpRoute(
                onAccountCreated = {
                    navController.navigate(Screen.SEARCH.route) {
                        popUpToTop(
                            navController
                        )
                    }
                }
            )
        }

        composable(Screen.SEARCH.route) {
            SearchRoute()
        }
    }
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive = true
    }
}

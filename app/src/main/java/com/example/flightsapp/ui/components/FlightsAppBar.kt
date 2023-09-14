package com.example.flightsapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsapp.R
import com.example.flightsapp.navigation.Screen
import com.example.flightsapp.ui.theme.FlightsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightsAppBar(
    navigateUp: () -> Unit,
    currentScreen: Screen
) {
    val topAppBarBackgroundColor: Color = if (currentScreen.isTopAppBarTransparent) {
        Color.Transparent
    } else {
        MaterialTheme.colorScheme.background
    }

    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = topAppBarBackgroundColor,
            navigationIconContentColor = Color.Black // Todo change back arrow color based on destination
        ),
        title = {},
        navigationIcon = {
            if (!currentScreen.isTopLevel) {
                IconButton(onClick = {
                    navigateUp()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back_arrow),
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FlightsAppBarPreview() {
    FlightsAppTheme {
        Box {
            FlightsAppBar(
                navigateUp = {},
                currentScreen = Screen.SIGN_IN
            )
        }
    }
}

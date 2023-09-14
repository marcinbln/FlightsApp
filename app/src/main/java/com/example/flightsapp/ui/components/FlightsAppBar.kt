package com.example.flightsapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R
import com.example.flightsapp.navigation.Screen
import com.example.flightsapp.ui.theme.FlightsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightsAppBar(
    navigateUp: () -> Unit,
    currentScreen: Screen?
) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = Color.Black
        ),
        title = {},
        navigationIcon = {
            if (currentScreen != null) {
                if (!currentScreen.isTopLevel) {
                    IconButton(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = navigateUp
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back_arrow),
                            contentDescription = null
                        )
                    }
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

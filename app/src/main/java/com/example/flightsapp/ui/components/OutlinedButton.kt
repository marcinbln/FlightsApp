package com.example.flightsapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R
import com.example.flightsapp.ui.theme.Blue500
import com.example.flightsapp.ui.theme.FlightsAppTheme

@Composable
fun OutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier.heightIn(min = dimensionResource(id = R.dimen.buttonHeight)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = ButtonDefaults.buttonElevation(2.dp),
        border = BorderStroke(1.dp, Blue500),
        onClick = { onClick() }
    ) {
        content()
    }
}

@Preview(name = "OutlinedButton", showBackground = true)
@Preview(
    name = "OutlinedButton night",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun OutlinedButtonPreview() {
    FlightsAppTheme {
        OutlinedButton(onClick = {}) {
            Text(text = "Click me")
        }
    }
}

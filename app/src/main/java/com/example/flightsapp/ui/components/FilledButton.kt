package com.example.flightsapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsapp.ui.theme.Blue700
import com.example.flightsapp.ui.theme.FlightsAppTheme

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier.heightIn(min = 46.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue700,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(2.dp),
        enabled = enabled,
        onClick = { onClick() }
    ) {
        content()
    }
}

@Preview(name = "DefaultButton", showBackground = true)
@Preview(
    name = "DefaultButton night",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultButtonPreview() {
    FlightsAppTheme {
        DefaultButton(onClick = {}) {
            Text(text = "Click me")
        }
    }
}

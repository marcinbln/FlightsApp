package com.example.flightsapp.core.common.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsapp.R
import com.example.flightsapp.ui.theme.FlightsAppTheme

@Composable
internal fun TextFieldWithValidation(
    label: Int,
    text: String,
    onInputChange: (String) -> Unit,
    isError: Boolean,
    error: Int?
) {
    val containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = onInputChange,
        isError = isError,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            unfocusedLabelColor = Color.Gray
        ),
        label = {
            Text(text = stringResource(id = label))
        },
        supportingText = {
            if (isError && error != null) {
                Text(text = stringResource(id = error))
            }
        }
    )
}

@Preview
@Composable
private fun TextFieldWithValidationPreview() {
    FlightsAppTheme {
        TextFieldWithValidation(
            label = R.string.login_email_hint,
            text = "",
            onInputChange = {},
            isError = false,
            error = R.string.input_error_email
        )
    }
}

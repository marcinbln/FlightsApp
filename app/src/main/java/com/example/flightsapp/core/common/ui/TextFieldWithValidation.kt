package com.example.flightsapp.core.common.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TextFieldWithValidation(
    label: Int,
    text: String,
    onInputChange: (String) -> Unit,
    isError: Boolean,
    error: Int?
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = onInputChange,
        isError = isError,
        colors = TextFieldDefaults.textFieldColors(
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

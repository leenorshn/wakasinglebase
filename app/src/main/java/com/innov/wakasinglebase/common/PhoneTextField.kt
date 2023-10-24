package com.innov.wakasinglebase.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign


@Composable
fun PhoneTextField(
    phone: TextFieldValue,
    onValueChange: (value: TextFieldValue) -> Unit,
    label: String
) {
    Row {

        TextField(
            value = phone,
            label={
               Text(text = label)
            },
            onValueChange = {value->
                onValueChange(value)
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done,
            ),
            supportingText = {
                Text(
                    text = "${phone.text.length} / 13",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                )
            },
        )
    }
}
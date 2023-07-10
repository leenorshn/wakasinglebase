package com.innov.wakasinglebase.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun CustomTextField(
    label:String,
    value:String,
    onChange:(value:TextFieldValue)->Unit

) {
    TextField(
        value = TextFieldValue(
            value
        ),
        label={
              Text(text = label)
        },
        onValueChange =onChange,
        modifier = Modifier.fillMaxWidth(),

    )
}
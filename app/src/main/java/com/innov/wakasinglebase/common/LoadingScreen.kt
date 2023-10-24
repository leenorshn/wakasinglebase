package com.innov.wakasinglebase.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.ui.theme.PrimaryColor

@Composable
fun LoadingScreen(message: String) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black, shape = RectangleShape)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_tiktok_compose),
                contentDescription = "",
                modifier = Modifier.size(80.dp)
            )
            32.dp.Space()
            Text(text = "$message ...", color = Color.Gray)
            56.dp.Space()
            CircularProgressIndicator(
                trackColor = Color.White,
                color = PrimaryColor,
                modifier = Modifier
            )
        }
    }
}
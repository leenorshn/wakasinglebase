package com.innov.wakasinglebase.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun StackedImage(image: String?) {
    Box(
        modifier = Modifier
            .size(44.dp)
    ) {
        var img =
            if (image.isNullOrEmpty()) "https://d2y4y6koqmb0v7.cloudfront.net/profil.png" else image
        AsyncImage(
            model = img,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(shape = CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    spotColor = Color.Black,
                    ambientColor = Color.Black,
                    clip = true,
                ),
            contentScale = ContentScale.Crop
        )
        // You can add more images or other composables on top of the base image here
    }
}
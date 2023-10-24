package com.innov.wakasinglebase.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.innov.wakasinglebase.ui.theme.White

@Composable
fun Avatar(
    image: String,
    onClick:()->Unit,
    size: Dp =50.dp
) {
    AsyncImage(
        model = image,
        contentDescription = null,
        modifier = Modifier
            .size(size = size)
            .border(
                BorderStroke(width = 1.dp, color = White), shape = RoundedCornerShape(12.dp)
            )
            .clip(shape = RoundedCornerShape(size/2))
            .clickable { onClick.invoke() }
            ,
        contentScale = ContentScale.Crop
    )

}


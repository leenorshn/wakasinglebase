package com.innov.wakasinglebase.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.screens.myprofil.edit_profile.UploadImageState
import com.innov.wakasinglebase.ui.theme.PrimaryColor

@Composable
fun Avatar(
    image: String?,
    state:UploadImageState,
    onClick: () -> Unit,
    size: Dp = 50.dp
) {
    val url= if(image.isNullOrEmpty()) R.drawable.profile else image
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier) {
            AsyncImage(
                model = url,
                contentDescription = null,
                modifier = Modifier
                    .size(size = size)
                    .border(
                        BorderStroke(width = 1.dp, color = Color.Gray),
                        shape = RoundedCornerShape(size / 2)
                    )
                    .clip(shape = RoundedCornerShape(size / 2))
                    .clickable { onClick.invoke() },
                contentScale = ContentScale.Crop
            )

        }
        TextButton(onClick = { onClick.invoke() }) {
            Text(text = "Change image")
        }
        if (state.isLoading){
            LinearProgressIndicator(color = PrimaryColor)
        }
        if (state.error!=null){
            Text("Error of uploading a image")
        }
    }

}


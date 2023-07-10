package com.innov.wakasinglebase.common


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.innov.wakasinglebase.R

@ExperimentalAnimationApi
@Composable
fun AutoBackgroundSlider(image: Int) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                contentScale= ContentScale.Crop,
                modifier = Modifier.fillMaxSize().fillMaxWidth()
            )
        }

}

@ExperimentalAnimationApi
@Composable
fun DisplayAutoBackgroundSlider() {
    val imageList = listOf(
        R.drawable.one,
    )

    AutoBackgroundSlider(imageList[0])
}


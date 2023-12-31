package com.innov.wakasinglebase.screens.createprofile.creatorprofile

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.core.utils.FileUtils
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.ui.theme.GrayMainColor
import com.innov.wakasinglebase.ui.theme.PrimaryColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by innov Victor on 3/25/2023.
 */

@Composable
fun VideoGrid(
    scrollState: ScrollState,
    videoList: PublicVideoState,
    onClickVideo: (video: VideoModel, index: Int) -> Unit
) {
    val context = LocalContext.current
    if (videoList.isLoading){
        LinearProgressIndicator(color = PrimaryColor)
    }
    if (videoList.videos.isNotEmpty()){
        LazyVerticalGrid(columns = GridCells.Fixed(3),
            modifier = Modifier
                .height(1000.dp)
                .nestedScroll(
                    remember {
                        object : NestedScrollConnection {
                            override fun onPreScroll(
                                available: Offset,
                                source: NestedScrollSource
                            ): Offset {
                                return if (available.y > 0) Offset.Zero else
                                    Offset(
                                        x = 0f,
                                        y = -scrollState.dispatchRawDelta(-available.y)
                                    )
                            }
                        }
                    }
                ),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp),
            content = {
                itemsIndexed(videoList.videos) { index, item ->
                    context.VideoGridItem(item, index, onClickVideo = onClickVideo)
                    // Text(text = item.videoId)
                }
            })
    }


}

@Composable
fun Context.VideoGridItem(item: VideoModel, index: Int, onClickVideo: (VideoModel, Int) -> Unit) {
    Box(
        modifier = Modifier
            .height(160.dp)
            .clickable {
                onClickVideo(item, index)
            }
    ) {
        var thumbnail by remember {
            mutableStateOf<Pair<Bitmap?, Boolean>>(Pair(null, true))  //bitmap, isShow
        }
        AsyncImage(
            model = thumbnail.first,
            contentDescription = "image thumbnail",
            modifier = Modifier
                .fillMaxSize()
                .background(GrayMainColor)
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.5f)
                            ), startY = 300f
                        )
                    )
                },
            contentScale = ContentScale.Crop
        )
        LaunchedEffect(key1 = true) {
            withContext(Dispatchers.IO) {
                val bm = FileUtils.extractThumbnailFromUrl(
                    item.videoLink
                )
                withContext(Dispatchers.Main) {
                    thumbnail = thumbnail.copy(first = bm, second = thumbnail.second)
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                painterResource(id = R.drawable.ic_play_outline),
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                tint = Color.Gray
            )
            Text(
                text = "${item.like} votes",
                color = Color.White,
                style = MaterialTheme.typography.labelMedium
            )
        }

    }
}

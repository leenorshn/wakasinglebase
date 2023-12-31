package com.innov.wakasinglebase.screens.myprofil.myvideos

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.core.utils.FileUtils
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.ui.theme.PrimaryColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun MyVideoScreen(
    navController: NavController,
    videoId: String?,
    viewModel: MyVideosViewModel = hiltViewModel()
) {
    var videos by remember {
        mutableStateOf<List<VideoModel>>(emptyList())
    }



    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(MyVideoEvent.OnVideosLoadedEvent("$videoId"))
    }

    val uiState by viewModel.viewState.collectAsState()
    val deleteState by viewModel.deleteState.collectAsState()
    LaunchedEffect(key1 = uiState, ){
        if (uiState?.videos?.isEmpty()==false){
            videos= uiState?.videos!!
        }
    }

//    LaunchedEffect(key1 = deleteState.success) {
//        if (deleteState.success) {
//            //viewModel.onTriggerEvent(MyVideoEvent.OnVideosLoadedEvent("$videoId"))
//            uiState?.videos?.let {
//                videos=it.filter{
//                    it.videoId==
//                }
//            }
//        }
//    }
    Scaffold(
        topBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Outlined.ArrowBack, "back")
                }
                Text(text = "My Videos", fontWeight = FontWeight.Medium, fontSize = 18.sp)
                Text(text = (videos.size.toString() + " Videos  ") )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            item {

            }
            if (uiState?.isLoading == true) {
                item {
                    LinearProgressIndicator(
                        color = PrimaryColor,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            if (uiState?.error != null) {
                item {
                    Text(text = "Error of loading data", color = Color.Red)
                }
            }

            if (uiState?.videos?.isEmpty() == true) {
                item {
                    Text(
                        text = "You don't have any video",
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp)
                    )
                }
            }
//
            if (uiState?.videos != null) {
                itemsIndexed(videos){ i,v->
                    MyVideoItem(
                        video = v,
                        onVideoClicked = {
                            navController.navigate(
                                DestinationRoute.VIDEO_DETAIL_ROUTE.replace(
                                    "{video}",
                                    v.videoId
                                )
                            )
                        },
                        onVideoDeleted = {
                            viewModel.onTriggerEvent(MyVideoEvent.OnVideoDeletedEvent(v.videoId))
                           videos= videos.filter { n->n.videoId!=v.videoId }
                        },

                    )
                }
            }


        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyVideoItem(
    video: VideoModel, onVideoClicked: () -> Unit, onVideoDeleted: () -> Unit,
) {
    var thumbnail by remember {
        mutableStateOf<Pair<Bitmap?, Boolean>>(Pair(null, true))  //bitmap, isShow
    }
    // var isFirstFrameLoad = remember { false }

    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO) {
            val bm = FileUtils.extractThumbnailFromUrl(
                video.videoLink
            )
            withContext(Dispatchers.Main) {
                thumbnail = thumbnail.copy(first = bm, second = thumbnail.second)
            }
        }
    }
    ListItem(
        modifier = Modifier.clickable {
            onVideoClicked.invoke()
        },
        leadingContent = {
            if (thumbnail.second) {
                AsyncImage(
                    model = thumbnail.first,
                    contentDescription = null,
                    modifier = Modifier
                        .height(80.dp)
                        .width(72.dp),
                    contentScale = ContentScale.Crop
                )
            }
        },
        supportingContent = {
            Text(
                text = "${video.category} video -- ${video.like} likes - ${video.comment} comments",
                color = Color.Gray,
                fontSize = 12.sp
            )
        },
        headlineContent = {
            Text(text = "${video.videoTitle}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        },
        trailingContent = {

                IconButton(onClick = { onVideoDeleted.invoke() }) {
                    Icon(Icons.Outlined.Delete, "delete")
                }

        }
    )
}

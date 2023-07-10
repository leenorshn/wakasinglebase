package com.innov.wakasinglebase.common

import android.graphics.Bitmap
import android.net.Uri
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.innov.wakasinglebase.core.utils.FileUtils
import com.innov.wakasinglebase.data.model.VideoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalFoundationApi::class)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun VideoPlayerX(
    videos: List<VideoModel>,
    pagerState: PagerState,
    pageIndex: Int,
    onSingleTap: (exoPlayer: ExoPlayer) -> Unit,
    onDoubleTap: (exoPlayer: ExoPlayer, offset: Offset) -> Unit,
    onVideoDispose: () -> Unit = {},
    onVideoGoBackground: () -> Unit = {}
) {
    val context = LocalContext.current
    var thumbnail by remember {
        mutableStateOf<Pair<Bitmap?, Boolean>>(Pair(null, true))  //bitmap, isShow
    }
    var isFirstFrameLoad = remember { false }
    val currentVideo = videos[pageIndex]

    LaunchedEffect(key1 = currentVideo.videoLink) {
        withContext(Dispatchers.IO) {
            val bm = FileUtils.extractThumbnailFromUrl(
                currentVideo.videoLink
            )
            withContext(Dispatchers.Main) {
                thumbnail = thumbnail.copy(first = bm, second = thumbnail.second)
            }
        }
    }

    val currentVideoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
            repeatMode = Player.REPEAT_MODE_ONE
            setMediaItem(MediaItem.fromUri(Uri.parse(currentVideo.videoLink)))
            playWhenReady = true
            prepare()
            addListener(object : Player.Listener {
                override fun onRenderedFirstFrame() {
                    super.onRenderedFirstFrame()
                    isFirstFrameLoad = true
                    thumbnail = thumbnail.copy(second = false)
                }
            })
        }
    }

    val nextVideoIndex = minOf(pageIndex + 1, videos.lastIndex)
    val nextVideo = videos[nextVideoIndex]

    val nextVideoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
            repeatMode = Player.REPEAT_MODE_ONE
            setMediaItem(MediaItem.fromUri(Uri.parse(nextVideo.videoLink)))
            prepare()
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val lifeCycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> {
                    currentVideoPlayer.pause()
                    nextVideoPlayer.pause()
                    onVideoGoBackground()
                }
                Lifecycle.Event.ON_START -> {
                    currentVideoPlayer.play()
                    nextVideoPlayer.play()
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifeCycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifeCycleObserver)
            currentVideoPlayer.release()
            nextVideoPlayer.release()
        }
    }

    DisposableEffect(key1 = currentVideoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED && isFirstFrameLoad) {
                    currentVideoPlayer.removeListener(this)
                    currentVideoPlayer.stop()
                    currentVideoPlayer.release()
                    if (pageIndex < videos.size - 1) {
                        val nextVideo = videos[pageIndex + 1]
                        nextVideoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(nextVideo.videoLink)))
                        nextVideoPlayer.prepare()
                    }
                }
            }
        }

        currentVideoPlayer.addListener(listener)
        onDispose {
            currentVideoPlayer.removeListener(listener)
        }
    }

    val playerView = remember {
        PlayerView(context).apply {
            player = currentVideoPlayer
            useController = false
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            thumbnail = thumbnail.copy(second = true)
            currentVideoPlayer.release()
            onVideoDispose()
        }
    }

    if (thumbnail.second) {
        AsyncImage(
            model = thumbnail.first,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

    AndroidView(
        factory = { playerView },
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                onSingleTap(currentVideoPlayer)
            }, onDoubleTap = { offset ->
                onDoubleTap(currentVideoPlayer, offset)
            })
        }
    )
}

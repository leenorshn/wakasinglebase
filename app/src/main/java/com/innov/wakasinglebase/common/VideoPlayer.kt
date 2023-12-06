package com.innov.wakasinglebase.common


import android.net.Uri
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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
import coil.request.ImageRequest
import com.innov.wakasinglebase.data.model.VideoModel


/**
 * Created by innov Victor on 3/16/2023.
 */
@OptIn(ExperimentalFoundationApi::class)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun VideoPlayer(
    video: VideoModel,
    pagerState: PagerState,
    pageIndex: Int,
    onSingleTap: (exoPlayer: ExoPlayer) -> Unit,
    onDoubleTap: (exoPlayer: ExoPlayer, offset: Offset) -> Unit,
    onVideoDispose: () -> Unit = {},
    onVideoGoBackground: () -> Unit = {}
) {
    val context = LocalContext.current



    if (pagerState.settledPage == pageIndex) {
        //val videoOptions = VideoOptions("${video.videoId}", VideoType.VOD /* For VOD, or VideoType.LIVE for Live */, "Mriz6mr0R8OOGidYspbYVVmE38YjG9EUer3TfrEGJea")
        val exoPlayer = remember(context) {
            ExoPlayer.Builder(context)
                //.setLoadControl(loadControl)
                .build().apply {->
                videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
                repeatMode = Player.REPEAT_MODE_ONE

                //setMediaSource(MediaItem.fromUri(Uri.parse(video.videoId)))

                setMediaItem(MediaItem.fromUri(Uri.parse(video.videoLink)))

                playWhenReady = true
                prepare()
                addListener(object : Player.Listener {
                    override fun onRenderedFirstFrame() {
                        super.onRenderedFirstFrame()

                    }
                })
            }
        }




        val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
        DisposableEffect(key1 = lifecycleOwner) {
            val lifeCycleObserver = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_STOP -> {
                        exoPlayer.pause()
                        onVideoGoBackground()
                    }
                    Lifecycle.Event.ON_START -> exoPlayer.play()
                    else -> {}
                }
            }
            lifecycleOwner.lifecycle.addObserver(lifeCycleObserver)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(lifeCycleObserver)
            }
        }

        val playerView = remember {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }

        DisposableEffect(key1 = AndroidView(factory = {
            playerView
        }, modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                onSingleTap(exoPlayer)
            }, onDoubleTap = { offset ->
                onDoubleTap(exoPlayer, offset)
            })
        }), effect = {
            onDispose {

                exoPlayer.release()
                onVideoDispose()
            }
        })
    }

    if (video.thumbnail!=null) {
       val context = LocalContext.current
        AsyncImage(
            model = ImageRequest.Builder(context = context).data(video.thumbnail).crossfade(200).build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

}

private const val MIN_BUFFER_MS = 3000 // Minimum video buffer duration in milliseconds
private const val MAX_BUFFER_MS = 10000 // Maximum video buffer duration in milliseconds
private const val MIN_PLAYBACK_START_BUFFER_MS = 2500 // Minimum buffer before starting playback in milliseconds
private const val MIN_PLAYBACK_RESUME_BUFFER_MS = 5000






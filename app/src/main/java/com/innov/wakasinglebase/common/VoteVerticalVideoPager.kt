package com.innov.wakasinglebase.common


import CompetitionModel
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.core.extension.formattedCount
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by innov Victor on 3/16/2023.
 */
@OptIn(ExperimentalFoundationApi::class)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun VoteVerticalVideoPager(
    modifier: Modifier = Modifier,
    videos: List<VideoModel>,
    competitionModel: CompetitionModel,
    initialPage: Int? = 0,
    onClickVote: (videoId: String) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = initialPage ?: 0,
        initialPageOffsetFraction = 0f,
        pageCount = { videos.size }

    )
    val coroutineScope = rememberCoroutineScope()
    val localDensity = LocalDensity.current

    val fling = PagerDefaults.flingBehavior(
        state = pagerState, lowVelocityAnimationSpec = tween(
            easing = LinearEasing, durationMillis = 300
        )
    )

    VerticalPager(
        state = pagerState,
        flingBehavior = fling,
        beyondBoundsPageCount = 1,
        modifier = modifier
    ) {
        var pauseButtonVisibility by remember { mutableStateOf(false) }
        var doubleTapState by remember {
            mutableStateOf(
                Triple(
                    Offset.Unspecified, //offset
                    false, //double tap anim start
                    0f //rotation angle
                )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()

        ) {
       // Column(verticalArrangement = Arrangement.Bottom) {

            VideoPlayer(
                videos[it], pagerState, it, onSingleTap = {
                    pauseButtonVisibility = it.isPlaying
                    it.playWhenReady = !it.isPlaying

                },
                onDoubleTap = { exoPlayer, offset ->
                    coroutineScope.launch {
                        videos[it].currentViewerInteraction.isLikedByYou = true
                        val rotationAngle = (-10..10).random()
                        doubleTapState = Triple(offset, true, rotationAngle.toFloat())
                        delay(400)
                        doubleTapState = Triple(offset, false, rotationAngle.toFloat())
                    }
                },
                onVideoDispose = { pauseButtonVisibility = false },
                onVideoGoBackground = { pauseButtonVisibility = false }

            )

            FooterUX(
                modifier = Modifier
                    .fillMaxWidth(),
                item = videos[it],
                title = competitionModel.name,
                onClickVote = {
                    onClickVote.invoke(it)
                },
                onClickUser = {}

            )









            AnimatedVisibility(
                visible = pauseButtonVisibility,
                enter = scaleIn(spring(Spring.DampingRatioMediumBouncy), initialScale = 1.5f),
                exit = scaleOut(tween(150)),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(36.dp)
                )
            }

            val iconSize = 110.dp
            AnimatedVisibility(visible = doubleTapState.second,
                enter = scaleIn(spring(Spring.DampingRatioMediumBouncy), initialScale = 1.3f),
                exit = scaleOut(
                    tween(700), targetScale = 1.58f
                ) + fadeOut(tween(600)) + slideOutVertically(
                    tween(600)
                ),
                modifier = Modifier.run {
                    if (doubleTapState.first != Offset.Unspecified) {
                        this.offset(x = localDensity.run {
                            doubleTapState.first.x.toInt().toDp().plus(-iconSize.div(2))
                        }, y = localDensity.run {
                            doubleTapState.first.y.toInt().toDp().plus(-iconSize.div(2))
                        })
                    } else this
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_like),
                    contentDescription = null,
                    tint = if (doubleTapState.second) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.8f
                    ),
                    modifier = Modifier
                        .size(iconSize)
                        .rotate(doubleTapState.third)
                )
            }

        }

    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FooterUX(
    modifier: Modifier,
    item: VideoModel,
    title:String,
    onClickVote: (videoId: String) -> Unit,
    onClickUser: (id: String) -> Unit,
) {

    var like by remember {
        mutableStateOf(item.like)
    }
    Column(
        modifier = Modifier.padding(horizontal = 20.dp).fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom
    ) {


        ListItem(
            modifier=Modifier.clip(shape= RoundedCornerShape(16)),
            leadingContent = {
                AsyncImage(
                    model = "${item.authorDetails?.profilePic}",
                    contentDescription = "",
                    contentScale= ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color.Gray.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(50)
                        )
                        .clip(RoundedCornerShape(50))
                )
            },
            overlineContent = {
                Text("Author", fontSize = 10.sp)
            },
            headlineContent = {
                Text("${item.authorDetails?.name}", fontSize = 14.sp)
            },
            trailingContent = {
                Text("${like.formattedCount()} votes", fontSize = 24.sp)
            },
            supportingContent = {
                Text("$title ", fontSize = 10.sp)
            }
        )

        8.dp.Space()

        24.dp.Space()
        CustomIconButton(
            modifier = Modifier.padding(horizontal = 32.dp),
            buttonText = "support me", icon = R.drawable.ic_like
        ) {
            onClickVote.invoke(item.videoId)
            like+=1
        }
        24.dp.Space()

    }
}






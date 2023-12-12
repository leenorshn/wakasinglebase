package com.innov.wakasinglebase.common


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.core.extension.formattedCount
import com.innov.wakasinglebase.core.utils.IntentUtils.share
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.ui.theme.Gray20
import com.innov.wakasinglebase.ui.theme.GrayLight
import com.innov.wakasinglebase.ui.theme.PrimaryColor
import com.innov.wakasinglebase.ui.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by innov Victor on 3/16/2023.
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun WakawakaVerticalVideoPager(
    modifier: Modifier = Modifier,
    videos: List<VideoModel>,
    initialPage: Int? = 0,
    showUploadDate: Boolean = false,
    onclickComment: (videoId: String) -> Unit,
    onclickFavourite: (videoId: String) -> Unit,
    onClickLike: (videoId: String,isLiked:Boolean) -> Unit,
    onClickVote:(videoId:String)->Unit,
    onClickAudio: (VideoModel) -> Unit,
    onClickUser: (userId: String) -> Unit,
    onClickFavourite: (isFav: Boolean) -> Unit = {},
    onClickShare: (() -> Unit)? = null
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

        Box(modifier = Modifier.fillMaxSize()) {
            VideoPlayer(videos[it], pagerState, it, onSingleTap = {
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

            Row() {
                RotatingAudioView()
            }


            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    FooterUi(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        item = videos[it],
                        showUploadDate = showUploadDate,
                        onClickAudio = onClickAudio,
                        onClickUser = onClickUser,
                    )

                    SideItems(
                        modifier = Modifier,
                        videos[it],
                        doubleTabState = doubleTapState,
                        onclickComment = onclickComment,
                        onClickUser = onClickUser,
                        onClickFavourite = onClickFavourite,
                        onClickShare = onClickShare,
                        onClickVote = onClickVote,
                        onClickLike = {s:String,b:Boolean->
                            onClickLike.invoke(s,b)
                        }
                    )
                }
                12.dp.Space()
            }


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


@Composable
fun SideItems(
    modifier: Modifier,
    item: VideoModel,
    doubleTabState: Triple<Offset, Boolean, Float>,
    onclickComment: (videoId: String) -> Unit,
    onClickUser: (userId: String) -> Unit,
    onClickVote: (videoId: String) -> Unit,
    onClickShare: (() -> Unit)? = null,
    onClickLike: (videoId: String,isLiked:Boolean) -> Unit,
    onClickFavourite: (isFav: Boolean) -> Unit
) {

    var likes by remember {
        mutableIntStateOf(item.like)
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = Unit, ){
        likes=item.like
    }
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        12.dp.Space()

        var isLiked by remember {
            mutableStateOf(item.currentViewerInteraction.isLikedByYou)
        }

        LaunchedEffect(key1 = doubleTabState) {
            if (doubleTabState.first != Offset.Unspecified && doubleTabState.second) {
                isLiked = doubleTabState.second
            }
        }

        if (item.category == "CHALLENGE") {
            VoteIconButton(
                item = item,
                likeCount = likes.formattedCount(),
                onClickVote ={
                    onClickLike.invoke(item.videoId,true)
                    likes += 1
                }
            )
        }else {
            LikeIconButton(
                isLiked = isLiked,
                likeCount = likes.formattedCount(),
                onLikedClicked = {
                    onClickLike.invoke(item.videoId,it)
                    isLiked = it
                    item.currentViewerInteraction.isLikedByYou = it
                    likes += 1

                })
        }
        20.dp.Space()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_comment),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(33.dp)
                    .clickable {
                        onclickComment(item.videoId)
                    })
//            Text(
//                text = item.comment.formattedCount(),
//                style = MaterialTheme.typography.labelMedium
//            )

        }

        20.dp.Space()
        ShareIconButton(
            item = item,
            onClickShare={
                onClickShare?.let { onClickShare.invoke() } ?: run {
                    context.share(
                        text = item.videoLink
                    )
                }
            }
        )



        32.dp.Space()
    }
}

@Composable
fun LikeIconButton(
    isLiked: Boolean, likeCount: String, onLikedClicked: (Boolean) -> Unit
) {

    val maxSize = 38.dp
    val iconSize by animateDpAsState(
        targetValue = if (isLiked) 34.dp else 32.dp,
        animationSpec = keyframes {
            durationMillis = 400
            24.dp.at(50)
            maxSize.at(190)
            26.dp.at(330)
            32.dp.at(400).with(FastOutLinearInEasing)
        }, label = "like button"
    )

    Column(
        modifier = Modifier
            //.height(40.dp)
            //.clip(RoundedCornerShape(10.dp))
            //.background(color = MaterialTheme.colorScheme.primary)
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                onLikedClicked(!isLiked)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_heart),
            contentDescription = null,
            tint = if (isLiked) MaterialTheme.colorScheme.primary else Color.White,
            modifier = Modifier.size(iconSize)
        )
        8.dp.Space()
        Text(text = likeCount, style = MaterialTheme.typography.labelMedium)
       // 16.dp.Space()
    }


}

@Composable
fun ShareIconButton(
    item: VideoModel,
    onClickShare:()->Unit
) {
   // val context= LocalContext.current
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)
    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_share),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onClickShare.invoke()
                }
        )

    }
}

@Composable
fun VoteIconButton(

     likeCount: String,
     item: VideoModel,
     onClickVote: (videoId: String) -> Unit
) {

//    val maxSize = 38.dp
//    val iconSize by animateDpAsState(
//        targetValue = if (isLiked) 33.dp else 32.dp,
//        animationSpec = keyframes {
//            durationMillis = 400
//            24.dp.at(50)
//            maxSize.at(190)
//            26.dp.at(330)
//            32.dp.at(400).with(FastOutLinearInEasing)
//        }, label = "like button"
//    )

    Box(
        modifier = Modifier
            .height(40.dp)
            .width(120.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colorScheme.primary)
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                onClickVote.invoke(item.videoId)
            }, contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                vertical = 8.dp, horizontal = 10.dp,
            )
        ) {


            Text(text = "Voter", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_heart),
                contentDescription = null,
                tint =  Color.White,
                modifier = Modifier.size(34.dp)
            )
        }
    }

    Text(text = likeCount, style = MaterialTheme.typography.labelMedium)
    16.dp.Space()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FooterUi(
    modifier: Modifier,
    item: VideoModel,
    showUploadDate: Boolean,
    onClickAudio: (VideoModel) -> Unit,
    onClickUser: (userId: String) -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
       if (item.product!=null){
           Text("for all the content", fontSize = 12.sp)
           OutlinedButton(

               onClick = { /*TODO navigate to product with this ID*/ },
               border= BorderStroke(0.7.dp, PrimaryColor,),
               shape= RoundedCornerShape(32),
               colors=ButtonDefaults.outlinedButtonColors(
                   backgroundColor = PrimaryColor,
                   contentColor = Color.White,

                   )
           ) {
               Icon(painter = painterResource(id = R.drawable.usd_money_24), contentDescription = "",modifier=Modifier.size(16.dp))
               12.dp.Space()
               Text(text = "  Purchase ", fontSize = 12.sp)

           }
       }

        4.dp.Space()
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
            item.authorDetails?.let { it.uid?.let { it1 -> onClickUser(it1) } }
        }) {
            //
            if (item.authorDetails?.profilePic.isNullOrEmpty()) {
                AsyncImage(
                    model = "https://d2y4y6koqmb0v7.cloudfront.net/profil.png",
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .border(
                            BorderStroke(width = 1.dp, color = White), shape = CircleShape
                        )
                        .clip(shape = CircleShape)
                        .clickable {
                            item.authorDetails?.let { it.uid?.let { it1 -> onClickUser.invoke(it1) } }
                        },
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = item.authorDetails?.profilePic,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .border(
                            BorderStroke(width = 1.dp, color = White), shape = CircleShape
                        )
                        .clip(shape = CircleShape)
                        .clickable {
                            item.authorDetails?.let { it.uid?.let { it1 -> onClickUser.invoke(it1) } }
                        },
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            item.authorDetails?.let {
                val name=if(!it.name.isNullOrEmpty()) it.name else "${"@User" + it.uid?.substring(0, 8) }-Waka"
                Text(
                    text =name ,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (showUploadDate) {
                Text(
                    text = " . ${item.createdAt} ago",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }
        10.dp.Space()
        val audioInfo: String = item.audioModel?.run {
            "Original sound - ${audioAuthor.uniqueUserName} - ${audioAuthor.name}"
        }
            ?: item.run { "Original sound - ${item.videoTitle} - ${item.description}" }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clickable {
                onClickAudio(item)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_music_note),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = audioInfo,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .basicMarquee()
            )
        }
    }
}


@Composable
fun RotatingAudioView() {
    val infiniteTransition = rememberInfiniteTransition(label = "infinity loop")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(animation = keyframes { durationMillis = 7000 }),
        label = "rotating audio"
    )

    Box() {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 8.dp
                )
        ) {
            Text(
                text = stringResource(R.string.waka_waka), style = TextStyle(
                    color = PrimaryColor,
                    fontSize = 24.sp, fontWeight = FontWeight.Bold,
                )
            )
            Box(modifier = Modifier.rotate(angle)) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(
                                    Gray20, Gray20, GrayLight, Gray20, Gray20,
                                )
                            ), shape = CircleShape
                        )
                        .size(46.dp), contentAlignment = Alignment.Center
                ) {
//Image(painter = , contentDescription = )
                    Image(
                        painter = painterResource(id = R.drawable.logo_tiktok_compose),

                        contentDescription = "logo",
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                }
            }
        }
    }

}



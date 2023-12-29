package com.innov.wakasinglebase.screens.comment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.data.model.CommentModel
import com.innov.wakasinglebase.ui.theme.DarkBlue
import com.innov.wakasinglebase.ui.theme.GrayMainColor
import com.innov.wakasinglebase.ui.theme.PrimaryColor

@Composable
fun CommentListScreen(
    viewModel: CommentListViewModel = hiltViewModel(),
    video:String?,
    onClickCancel: () -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()
    val user = viewModel.user

    var comments = remember {
        mutableStateListOf<CommentModel>()
    }


    LaunchedEffect(key1 = true){
        viewModel.onTriggerEvent(
            CommentEvent.OnLoadCommentsEvent(video)
        )
    }

    LaunchedEffect(key1 = viewState){
        Log.d("COMMENT_STATE",viewState.toString())
        viewState?.comments?.let {list->
            comments.addAll(list)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight(0.5f)
    ) {
        12.dp.Space()
        if (viewState?.isLoading==true){
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color= PrimaryColor,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "${comments.size } ${stringResource(id = R.string.comments)}",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.align(Alignment.Center)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable {
                        onClickCancel()
                    }
            )
        }


        6.dp.Space()
        LazyColumn(contentPadding = PaddingValues(top = 4.dp), modifier = Modifier.weight(1f)) {

                items(comments) {
                    CommentItem(it)
                }

        }




        CommentUserField(
            image = user.profilePic,
            onSendComment = {

                comments.add(CommentModel(author = user,comment=it, createdAt = ""))
                viewModel.onTriggerEvent(CommentEvent.OnPublishEvent(
                    videoId = "$video",
                    comment=it,
                ))

                Log.d("COMMENT_STATE",comments.joinToString ())
            }
        )
    }
}


@Composable
fun CommentItem(item: CommentModel) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val (profileImg, name, comment, createdOn, reply, like, dislike) = createRefs()

        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(item.author.profilePic)
            .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(GrayMainColor)
                .constrainAs(profileImg) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                })


        Text(text = "${item.author.name}",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(profileImg.end, margin = 12.dp)
                top.linkTo(profileImg.top)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            })
        Text(text = item.comment ?: "auu",
            style = MaterialTheme.typography.bodySmall,
            color = DarkBlue,
            modifier = Modifier.constrainAs(comment) {
                start.linkTo(name.start)
                top.linkTo(name.bottom, margin = 5.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            })
        Text(text = "${item.createdAt} ",
            color=Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.constrainAs(createdOn) {
            start.linkTo(name.start)
            top.linkTo(comment.bottom, margin = 5.dp)
        })

//        Text(text = stringResource(id = R.string.reply),
//            style = MaterialTheme.typography.labelMedium,
//            modifier = Modifier.constrainAs(reply) {
//                start.linkTo(createdOn.end, margin = 16.dp)
//                top.linkTo(createdOn.top)
//                end.linkTo(like.end, margin = 4.dp)
//                width = Dimension.fillToConstraints
//            })

//        Row(
//            modifier = Modifier.constrainAs(like) {
//                bottom.linkTo(reply.bottom)
//                end.linkTo(dislike.start, margin = 24.dp)
//            },
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(3.dp)
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_like_outline),
//                contentDescription = null,
//                modifier = Modifier.size(18.dp)
//            )
//            item.totalLike.takeIf { it != 0L }?.let {
//                Text(text = it.toString(), fontSize = 13.sp, color = SubTextColor)
//            }
//
//        }

//        Row(
//            modifier = Modifier.constrainAs(dislike) {
//                bottom.linkTo(reply.bottom)
//                end.linkTo(parent.end)
//            },
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(3.dp)
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_dislike_outline),
//                contentDescription = null,
//                modifier = Modifier.size(18.dp)
//            )
//            // Text(text = "") //dislike not display
//        }
    }
    24.dp.Space()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentUserField(
    image:String?,
    onSendComment:(comment:String)->Unit,

) {
    var comment by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .shadow(elevation = (0.4).dp)
            .padding(horizontal = 16.dp)
    ) {
        HighlightedEmoji.values().toList().let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                it.forEach { emoji ->
                    Text(text = emoji.unicode, fontSize = 25.sp,
                        modifier = Modifier.clickable {
                            comment+=emoji.unicode
                        })
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = image, contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        shape =
                        CircleShape, color = GrayMainColor
                    )
                    .clip(
                        shape =
                        CircleShape,
                    )
            )

            OutlinedTextField(value = comment,
                onValueChange = {
                    comment=it
                },
                shape = RoundedCornerShape(36.dp),
                placeholder = {
                    Text(text = stringResource(R.string.add_comment))
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = GrayMainColor,
                    focusedContainerColor = GrayMainColor,
                    unfocusedBorderColor = Color.Transparent
                ),
                modifier = Modifier
                    .height(64.dp)
                    .weight(1f),
                trailingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.padding(end = 10.dp, start = 2.dp)
                    ) {

                       Text(text = "send", modifier = Modifier.clickable {
                            onSendComment.invoke(comment)
                           comment=""
                       })
                    }

                }

            )
        }
    }
}
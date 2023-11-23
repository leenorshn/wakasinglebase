package com.innov.wakasinglebase.screens.conversation


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.core.extension.LargeSpace
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.ui.theme.PrimaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen(
    navController: NavController,
    viewModel: ConversationViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val chatViewState = viewModel.conversationState.value
    val conversationState = viewModel.uiState.value



    LaunchedEffect(key1 = conversationState.success) {
        if (conversationState.success) {
            viewModel.onTriggerEvent(ConversationEvent.EventFetchUserEvent)
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Chats") }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = "")
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = PrimaryColor,
                onClick = {
                    navController.navigate(DestinationRoute.NEW_CHAT)
                }) {
                Icon(painter = painterResource(id = R.drawable.messages_24), contentDescription = "")
            }
        }


    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                if (viewState?.isLoading == true) {
                    LinearProgressIndicator(
                        trackColor = PrimaryColor,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                16.dp.Space()
            }
            if (chatViewState.conversations != null) {
                items(chatViewState.conversations) { convers ->
                    val avatar =
                        if (convers.receiver.profilePic.isNullOrEmpty()) "https://d2y4y6koqmb0v7.cloudfront.net/profil.png"
                        else convers.receiver.profilePic
                    val name = if (convers.receiver.name.isNullOrEmpty()) "@User${
                        convers.receiver.uid
                    }" else convers.receiver.name

                    ListItem(
                        leadingContent = {
                            AsyncImage(
                                model = avatar,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(50))
                            )
                        },
                        headlineContent = {
                            Text(
                                text = "$name ",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,

                                )
                        },
                        supportingContent = {
                            Text(
                                text = "${convers.chats[0].message} \n ${convers.chats[0].createdAt}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        },

                        )
                    Divider()

                }
            }



            item {
                80.dp.Space()
            }
        }
        LargeSpace()

    }

}






package com.innov.wakasinglebase.screens.friends

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.common.CustomButton
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendScreen(
    navController: NavController,
    viewModel: FriendViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Friends") }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Refresh, contentDescription = "")
                }
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                //.padding(start = 16.dp)
        ) {
            item {
                Divider(modifier = Modifier.padding(end = 16.dp))
                if (viewState?.isLoading == true) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = PrimaryColor
                    )
                }
                if (viewState?.error != null) {
                    Text(text = "${viewState?.error}")
                }
            }

            if (viewState?.friends != null) {
                if (viewState?.friends!!.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "You don't have more connexion in you community")
                            CustomButton(buttonText = "Find friends") {
                                navController.navigate(DestinationRoute.FOLLOWER_ROUTE)
                            }
                        }
                    }
                } else {
                    items(viewState?.friends!!) { friend ->
                        ListItem(
                            leadingContent = {
                                AsyncImage(model =friend.profilePic , contentDescription ="",
                                    contentScale= ContentScale.Crop,
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(RoundedCornerShape(50)))
                            },

                            headlineContent = {
                            Text(text = friend.name.ifEmpty { friend.phone })
                        },
                            supportingContent = {
                                Text(text = friend.phone, fontSize = 12.sp, color = Color.Gray)
                            },
                            trailingContent = {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Outlined.FavoriteBorder,
                                        contentDescription = "",
                                        tint = PrimaryColor,
                                    )
                                }
                            })
                    }
                }
            }
        }
    }
}
package com.innov.wakasinglebase.screens.threads.follower

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.common.CustomButton
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.ui.theme.PrimaryColor

@Composable
fun FollowItem(user: UserModel, followState: FollowState, onFollowingUser: (id: String) -> Unit) {
    ListItem(headlineContent = {
        (if (user.name == "") {
            "${user.phone}"
        } else {
            user?.name
        })?.let { Text(text = it) }
    },
        leadingContent = {
            if (user.profilePic != null) {
                AsyncImage(
                    model = user.profilePic, contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(50))
                )
            } else {
                AsyncImage(
                    model = "https://d2y4y6koqmb0v7.cloudfront.net/profil.png",
                    contentDescription = "",
                    contentScale= ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(50),),
                )
            }

        },
        trailingContent = {
            if (!followState.done) {
                CustomButton(buttonText = "Follow") {
                    onFollowingUser.invoke(user.uid!!)
                }
            }
            if (followState.isLoading) {
                CircularProgressIndicator(color = PrimaryColor)
            }
            if (followState.done) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "",
                    tint = PrimaryColor
                )
            }

        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowerScreen(
    navController: NavController,
    viewModel: FollowerViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val followState by viewModel.followState.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                actions = {
                    TextButton(
                        //enabled = viewModel.isValidFollowerList(),
                        onClick = {
                            navController.navigate(DestinationRoute.MAIN_NAV_ROUTE)
                        }) {
                        Text("Finish")
                    }
                })
        },
        bottomBar = {
            CustomButton(buttonText = "Finish", modifier = Modifier.fillMaxWidth(0.8f)) {
                navController.navigate(DestinationRoute.MAIN_NAV_ROUTE)
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                Text(text = "Connaissez-vous ces personnes")
            }
            if (viewState?.isLoading == true) {
                item {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = PrimaryColor
                    )
                }
            }

            if (viewState?.listUser != null) {
                items(viewState?.listUser!!) { user ->
                    FollowItem(user = user, followState, onFollowingUser = {
                        viewModel.onTriggerEvent(FollowerEvent.OnFollowingUser(it))
                    })
                }
            }
        }
    }
}
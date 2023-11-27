package com.innov.wakasinglebase.screens.community.roomCommunity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.ui.theme.PrimaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomCommunityScreen(
    navController: NavController,
    communityId: String?,
    viewModel: RoomCommunityViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    var expanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(RoomCommunityEvent.OnLoadCommunity(communityId!!))
    }
    if (viewState?.isLoading == true) {
        Scaffold(
            topBar = {
                TopAppBar(title = {})
            }
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it),
                color = PrimaryColor,
            )
        }
    }
    if (viewState?.error != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                )
            }
        ) {
            Text(text = "${viewState?.error}", modifier = Modifier.padding(it))
        }
    }
    viewState?.community?.let { community ->
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        AsyncImage(
                            model = "", contentDescription = "",

                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(horizontal = 0.5.dp)
                                .background(
                                    color = Color.Gray.copy(alpha = 0.5f,),
                                    shape = RoundedCornerShape(50)
                                )
                                .clip(RoundedCornerShape(50))
                        )
                    },
                    title = { Text(text = "${community.name} ") },
                    actions = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Outlined.MoreVert,
                                contentDescription = "",
                                tint = Color.Black,
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            // Content of your overlay menu
                            DropdownMenuItem(
                                text = {
                                    Text("Add member")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Person,
                                        contentDescription = ""
                                    )
                                },
                                onClick = {
                                    // Handle item click
                                    expanded = false
                                })
                            DropdownMenuItem(
                                text = {
                                    Text("Add admin")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Person,
                                        contentDescription = ""
                                    )
                                },
                                onClick = {
                                    // Handle item click
                                    expanded = false
                                })
                            DropdownMenuItem(
                                text = {
                                    Text("Close discussion")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Close,
                                        contentDescription = ""
                                    )
                                },
                                onClick = {
                                    // Handle item click
                                    expanded = false
                                })
                        }
                    })
            },
            bottomBar = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp, start = 24.dp, end = 24.dp),
                ) {
                    ExtendedFloatingActionButton(
                        containerColor = PrimaryColor,
                        contentColor = Color.White,
                        modifier = Modifier.width(170.dp),
                        onClick = { }) {

                        Text("Record")
                        Spacer(modifier = Modifier.width(24.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_micro_cercle),
                            contentDescription = ""
                        )

                    }
                    ExtendedFloatingActionButton(
//                        containerColor = Color.Black,
//                        contentColor = PrimaryColor,
                        modifier = Modifier.width(170.dp),
                        onClick = { }) {
                        Text("Live")
                        Spacer(modifier = Modifier.width(24.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_microphone_24),
                            contentDescription = ""
                        )
                    }

                }
            }

        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(1f)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(color = Color.Gray.copy(alpha = 0.1f))
                ) {
                    LazyVerticalGrid(
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        columns = GridCells.Adaptive(84.dp)
                    ) {
                        itemsIndexed(community.members) { index, item ->
                            Card {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,


                                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
                                ) {
                                    var img =
                                        if (item.profilePic.isNullOrEmpty()) "https://d2y4y6koqmb0v7.cloudfront.net/profil.png" else item.profilePic
                                    AsyncImage(
                                        model = img, contentDescription = "",
                                        contentScale = ContentScale.Crop,

                                        modifier = Modifier
                                            .size(72.dp)
                                            .clip(
                                                RoundedCornerShape(50)
                                            ),
                                    )
                                    Text(
                                        text = "@${item.name}",
                                        color = Color.Black,
                                        fontSize = 13.sp,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1,
                                    )
                                }
                            }
                        }
                    }

                }
                Box(
                    modifier = Modifier
                        .weight(0.2f)
                        .background(color = Color.Red.copy(alpha = 0.1f))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                            .padding(8.dp),
                    ) {
                        OutlinedIconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.radio_24),
                                ""
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Image(
                            painter = painterResource(id = R.drawable.spectre_bg),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth(1f)
                        )

                    }
                }
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(

                        verticalArrangement = Arrangement.spacedBy(6.dp),

                    ) {
                       // itemsIndexed(community.admins) { index, item ->
                        for (item in community.admins){
                            Card {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,


                                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
                                ) {
                                    var img =
                                        if (item.profilePic.isNullOrEmpty()) "https://d2y4y6koqmb0v7.cloudfront.net/profil.png" else item.profilePic
                                    AsyncImage(
                                        model = img, contentDescription = "",
                                        contentScale = ContentScale.Crop,

                                        modifier = Modifier
                                            .size(72.dp)
                                            .clip(
                                                RoundedCornerShape(50)
                                            ),
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "@${item.name}",
                                        color = Color.Black,
                                        fontSize = 13.sp,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1,
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }

    }
}
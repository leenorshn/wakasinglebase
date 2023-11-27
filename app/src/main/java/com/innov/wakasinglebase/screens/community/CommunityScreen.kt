package com.innov.wakasinglebase.screens.community

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.data.model.CommunityModel
import com.innov.wakasinglebase.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    navController: NavController,
    viewModel: CommunityViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        containerColor = Color.Gray.copy(alpha = 0.1f),
        topBar = {
            TopAppBar(
                title = { Text(text = "Communities ", fontWeight = FontWeight.SemiBold) },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = "")
                    }
                })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = PrimaryColor,
                contentColor = Color.White,
                onClick = {
                    navController.navigate(DestinationRoute.NEW_COMMUNITY_ROUTE)
                }) {
                Icon(Icons.Outlined.Add, contentDescription = "")
                Text(text = "New community")
            }
        }
    ) {

        if (viewState?.isLoading == true) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = PrimaryColor
            )
        }

        if (viewState?.error != null) {
            Text(text = "${viewState?.error}")
        }
        if (viewState?.communities != null) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                //.padding(start = 16.dp)
            ) {
                item {
                    24.dp.Space()
                }
                viewState?.communities?.let { communities ->


                    items(communities) { community ->

                        CommunityItem(community = community, onClick = {
                            navController.navigate(DestinationRoute.ROOM_COMMUNITY_ROUTE.replace("{community}",it))
                        })

                    }
                }
                item {
                    Spacer(modifier = Modifier.height(140.dp))
                }
            }
        }
    }
}

@Composable
fun StackedImage(image: String?) {
    Box(
        modifier = Modifier
            .size(44.dp)
    ) {
        var img =
            if (image.isNullOrEmpty()) "https://d2y4y6koqmb0v7.cloudfront.net/profil.png" else image
        AsyncImage(
            model = img,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(shape = CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    spotColor = Color.Black,
                    ambientColor = Color.Black,
                    clip = true,
                ),
            contentScale = ContentScale.Crop
        )
        // You can add more images or other composables on top of the base image here
    }
}

@Composable
fun AudioItem() {
    Box(
        modifier = Modifier
            // .height(24.dp)
            .fillMaxWidth()
            .background(
                color = Color.Red.copy(alpha = 0.02f),
                RoundedCornerShape(50)
            )
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedIconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.lister_la_musique_24),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = "Listening ...")
        }
    }
}

@Composable
fun CommunityItem(community:CommunityModel,onClick:(id:String)->Unit) {
    val context= LocalContext.current
    Card(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp,
            focusedElevation = 3.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFBF6FC)
        ),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable { onClick.invoke(community.id) }
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            val image = ImageRequest.Builder(context = context)
                .data(community.logo).crossfade(200).build()

            ListItem(
                modifier = Modifier
                    .height(100.dp)
                    .clip(
                        RoundedCornerShape(
                            12
                        )
                    ),
                headlineContent = {
                    Text(
                        text = "${community.name} ",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                supportingContent = {
                    Text(text = "${community.philosophy} ")
                },
                trailingContent = {
//                                            AsyncImage(
//                                                model = ,
//                                                contentDescription = "",
//                                                contentScale = ContentScale.Crop,
//                                                modifier = Modifier
//                                                    .size(44.dp)
//                                                    .clip(RoundedCornerShape(50))
//                                            )
                    Icon(
                        painter = painterResource(id = R.drawable.megaphone_24),
                        contentDescription = ""
                    )
                })
            8.dp.Space()
            AudioItem()
            8.dp.Space()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy((-24).dp)
                    ) {
                        for (t in community.members.take(4)) {
                            StackedImage(
                                image = t.profilePic
                            )
                        }
                    }
                    //Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "${community.members.size} members",
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
                //Spacer(modifier = Modifier.width(12.dp))
                Row (verticalAlignment = Alignment.CenterVertically){
                    Text(text = "${community.subscription} $/moth")
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedIconButton(
                        border = BorderStroke(color=Color.Gray,width=0.7.dp),
                        onClick = {
                            onClick.invoke(community.id)
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.link_24),
                            contentDescription = "",
                            tint=Color.Blue,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }
            }
        }
        12.dp.Space()

    }
}
package com.innov.wakasinglebase.screens.competition


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.common.StackedImage
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.core.extension.LargeSpace
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.ui.theme.PrimaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompetitionScreen(
    navController: NavController,
    viewModel: CompetitionViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val currentUser=uiState.user

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray.copy(alpha = 0.2f)),
        topBar = {
            TopAppBar(title = { Text(text = "Competitions") }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = "")
                }
            })
        },


        ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
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
            if (viewState?.competitions != null) {
                viewState?.competitions?.let {
                    items(it) { competition ->
                        Card(
                            modifier = Modifier.padding(bottom = 16.dp),
                            elevation = CardDefaults.elevatedCardElevation(
                                defaultElevation = 3.dp,
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                            )
                            {
                                AsyncImage(
                                    model = "${competition.banner}",
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        // .padding(bottom = 23.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                topStartPercent = 5,
                                                topEndPercent = 5
                                            )
                                        )
                                )

                                ListItem(
                                    overlineContent = {
                                        Text(text = "TamTam-Competition")
                                    },

                                    headlineContent = {
                                        Text(
                                            text = "${competition.name} ",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Medium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,

                                            )
                                    },
                                    supportingContent = {
                                        Text(
                                            text = "${competition.detail} ",
                                            fontSize = 14.sp,
                                            color = Color.Gray,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                    },

                                    )
                                4.dp.Space()
                               Box(modifier = Modifier.padding(horizontal = 16.dp)){
                                  // for (t in competition.participants.take(4)) {
                                       Row(
                                           horizontalArrangement = Arrangement.spacedBy((-14).dp)
                                       ) {
                                           for (t in competition.participants.take(4)) {
                                               StackedImage(
                                                   image = t.profilePic
                                               )
                                           }
                                      // }
                                   }
                               }
                                4.dp.Space()
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp),
                                    horizontalArrangement = Arrangement.End
                                ) {

                                    if(competition.videos.isNotEmpty()){
                                        ExtendedFloatingActionButton(
                                            containerColor = PrimaryColor,
                                            onClick = {
                                                navController.navigate(
                                                    DestinationRoute.WATCH_COMPETITION_ROUTE.replace(
                                                        "{id}",
                                                        competition.id
                                                    )
                                                )
                                            }) {
                                            Text(text = "Watch")
                                            Spacer(modifier = Modifier.width(24.dp))
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_play_outline),
                                                contentDescription = ""
                                            )
                                        }
                                    }else{
                                        Spacer(modifier = Modifier.width(24.dp))
                                    }
                                }
                            }
                        }


                    }
                }

            }



            item {
                80.dp.Space()
            }
        }
        LargeSpace()

    }

}






package com.innov.wakasinglebase.screens.competition.watch

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.core.DestinationRoute.VOTE_COMPETITION_ROUTE


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchCompetitionScreen(
    navController: NavController,
    id: String,
    viewModel: WatchCompetitionViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val userState by viewModel.userState.collectAsState()



    LaunchedEffect(key1 = true) {
       // Log.d("WATCH", id)
        viewModel.onTriggerEvent(WatchCompetitionEvent.OnLoadCompetitionEvent(id))
    }

    var topBarAlpha by remember { mutableFloatStateOf(1f) }
    val scrollState = rememberScrollState()

    // Adjust this value based on when you want the top bar to start fading out
    val fadeOutThreshold = 100.dp

    // Use LocalDensity to convert dp to pixels
    val fadeOutThresholdPixels = with(LocalDensity.current) { fadeOutThreshold.toPx() }

    LaunchedEffect(scrollState.value) {
        snapshotFlow { scrollState.value }
            .collect { scrollValue ->
                topBarAlpha = 1f - (scrollValue / fadeOutThresholdPixels).coerceIn(0f, 1f)
            }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray.copy(alpha = 0.3f)),
        topBar = {
                 //.alpha(topBarAlpha)
            viewState?.competition?.let {comp->
                Column(
                    modifier = Modifier.alpha(topBarAlpha)
                ) {
                    AsyncImage(
                        model = comp.banner,
                        contentDescription = " banner ",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.height(140.dp)
                    )
                }
            }

        },

    ) {

        if (viewState?.isLoading==true){
           // Shummer loading
            Text(text = "Shimmer loading")
        }
        Column {

            LazyVerticalGrid(
                //state=scrollState,
                modifier = Modifier.padding(it),
                columns = GridCells.Fixed(2),
            ) {
                viewState?.competition?.let { competition ->
                    items(competition.videos) { video ->
                        Card(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                                .clickable {
                                    navController.navigate(
                                        VOTE_COMPETITION_ROUTE.replace(
                                            "{id}",
                                            competition.id
                                        )
                                    )
                                }
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(280.dp)
                                    .fillMaxWidth()
                            ) {
                                AsyncImage(
                                    model = video.thumbnail,
                                    contentDescription = " video ",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.height(280.dp)
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_play),
                                    contentDescription = "play image",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .align(alignment = Alignment.Center)
                                        .size(44.dp)
                                )
                                Text(text = "@${video.authorDetails?.name?.replace(" ", "_")}",
                                    color=Color.White,
                                    modifier= Modifier
                                        .align(alignment = Alignment.BottomStart)
                                        .padding(6.dp))
                            }
                        }
                    }
                }
            }
        }



    }


}
package com.innov.wakasinglebase.screens.competition.watch

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.ui.theme.PrimaryColor


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WatchCompetitionScreen(
    navController: NavController,
    id:String,
    viewModel: WatchCompetitionViewModel= hiltViewModel()
) {
    val  viewState by viewModel.viewState.collectAsState()
    val joinState by viewModel.joinState.collectAsState()
    val context= LocalContext.current

    LaunchedEffect(key1 = true, ){
        Log.d("WATCH",id)
        viewModel.onTriggerEvent(WatchCompetitionEvent.OnLoadCompetitionEvent(id))
    }

    LaunchedEffect(key1 = joinState, ){
        if(joinState.success){
            Toast.makeText(context,"Thank you for your support",Toast.LENGTH_LONG).show()
        }
        if(joinState.error!=null){
            Toast.makeText(context,"Error acquire",Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray.copy(alpha = 0.2f)),
        topBar = {
            TopAppBar(title = { Text(text = "Competition $") }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = "")
                }
            })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = PrimaryColor,
                contentColor = Color.White,
                onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Add, contentDescription = " ")
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Ajouter video")
            }
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier.padding(it),
            columns = GridCells.Fixed(2), ){
            viewState?.competition?.let {competition->
                items(competition.videos) {video->
                   Card(
                       modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                   ) {
                       AsyncImage(model =video.thumbnail ,
                           contentDescription =" video ",
                           contentScale = ContentScale.Crop,
                           modifier = Modifier.height(320.dp))
                   }
                }
            }
        }
    }

//    Scaffold {
//        Column (modifier = Modifier
//            .padding(it)
//            .fillMaxSize()){
//            if (viewState?.competition!=null){
//                viewState?.competition?.let {
//                    VoteVerticalVideoPager(
//                        videos = it.videos,
//                        initialPage = 0,
//                        competitionModel = it,
//                        onClickVote = {
//                            viewModel.onTriggerEvent(WatchCompetitionEvent.OnSubmitVoteVideoEvent(it))
//                        }
//
//                    )
//                }
//            }
//        }
//    }
}
package com.innov.wakasinglebase.screens.competition.watch

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.common.VoteVerticalVideoPager


@OptIn(ExperimentalFoundationApi::class)
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

    Scaffold {
        Column (modifier = Modifier
            .padding(it)
            .fillMaxSize()){
            if (viewState?.competition!=null){
                viewState?.competition?.let {
                    VoteVerticalVideoPager(
                        videos = it.videos,
                        initialPage = 0,
                        competitionModel = it,
                        onClickVote = {
                            viewModel.onTriggerEvent(WatchCompetitionEvent.OnSubmitVoteVideoEvent(it))
                        }

                    )
                }
            }
        }
    }
}
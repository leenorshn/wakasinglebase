package com.innov.wakasinglebase.screens.competition.vote

import android.util.Log
import android.widget.Toast
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
import com.innov.wakasinglebase.common.WakawakaVerticalVideoPager
import com.innov.wakasinglebase.core.DestinationRoute

@Composable
fun VotePlayScreen(
    navController:NavController,
    viewModel: VotePlayerViewModel= hiltViewModel(),
    id:String,


) {
    val joinState by viewModel.joinState.collectAsState()
    val viewState by viewModel.viewState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        Log.d("WATCH", id)
        viewModel.onTriggerEvent(VoteCompetitionEvent.OnLoadCompetitionEvent(id))
    }


    LaunchedEffect(key1 = joinState) {
        if (joinState.success) {
            Toast.makeText(context, "Thank you for your support", Toast.LENGTH_LONG).show()
        }
        if (joinState.error != null) {
            Toast.makeText(context, "Error acquire", Toast.LENGTH_LONG).show()
        }
    }
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            viewState?.competition?.let {
//                VoteVerticalVideoPager(
//                    videos = it.videos,
//                    initialPage = 0,
//                    competitionModel = it,
//                    onClickVote = {
//                        //onClickVote.invoke(it)
//                        viewModel.onTriggerEvent(VoteCompetitionEvent.OnSubmitVoteVideoEvent(it))
//                    }
//
//                )

                WakawakaVerticalVideoPager(
                    videos = it.videos,
                    onclickComment = {
                        navController.navigate(DestinationRoute.COMMENT_BOTTOM_SHEET_ROUTE.replace("{video}",it)) },
                    onClickLike = {s:String ,b:Boolean->
                        viewModel.onTriggerEvent(VoteCompetitionEvent.OnSubmitVoteVideoEvent(s))
                    },
                    onclickFavourite = {},
                    onClickAudio = {},
                    onClickVote = {},
                    onClickUser = { userId -> navController.navigate("${DestinationRoute.CREATOR_PROFILE_ROUTE}/$userId") }
                )
            }
        }
    }


}
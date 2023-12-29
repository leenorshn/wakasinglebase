package com.innov.wakasinglebase.screens.home.foryou

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.AuthState
import com.innov.wakasinglebase.common.WakawakaVerticalVideoPager
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.core.DestinationRoute.AUTH_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.CREATOR_PROFILE_ROUTE
import com.innov.wakasinglebase.ui.theme.DarkBlue
import com.innov.wakasinglebase.ui.theme.DarkPink
import com.innov.wakasinglebase.ui.theme.PrimaryColor

/**
 * Created by innov Victor on 3/14/2023.
 */
@Composable
fun ForYouTabScreen(
    navController: NavController,
    authState: AuthState,
    viewModel: ForYouViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val likeState by viewModel.likeState
    val context= LocalContext.current
    LaunchedEffect(key1 = likeState.isLiked ){
        if (likeState.isLiked){
            Toast.makeText(context,"video liked",Toast.LENGTH_LONG).show()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(DarkPink, DarkBlue)
                )
            )
    ) {
        if (viewState?.forYouPageFeed == null){

                CircularProgressIndicator(
                    trackColor = Color.White,
                    color = PrimaryColor,
                    modifier = Modifier
                        .align(Alignment.Center)
                )



        }else{

        viewState?.forYouPageFeed?.let {
          //  Log.e("Waka",it.size.toString())

           WakawakaVerticalVideoPager(
                videos = it,
                onclickComment = {
                                 if (authState.success){
                                     navController.navigate(DestinationRoute.COMMENT_BOTTOM_SHEET_ROUTE.replace("{video}",it))
                                 }else{
                                     navController.navigate(AUTH_ROUTE)
                                 }
                     },
                onClickLike = {s:String ,b:Boolean->
                    if (authState.success){
                        viewModel.onTriggerEvent(ForYouEvent.OnLikeVideo(s))
                    }else{
                        navController.navigate(AUTH_ROUTE)
                    }

                },
                //onclickFavourite = {},
                onClickAudio = {},
              // onClickVote = {},
                onClickUser = { userId -> navController.navigate("$CREATOR_PROFILE_ROUTE/$userId") }
            )
        } }
    }
}

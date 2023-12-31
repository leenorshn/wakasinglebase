package com.innov.wakasinglebase

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.innov.wakasinglebase.common.LoadingScreen
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.screens.camera.cameraMediaNavGraph
import com.innov.wakasinglebase.screens.camera.publication.publicationNavGraph
import com.innov.wakasinglebase.screens.camera.upload.uploadNavGraph
import com.innov.wakasinglebase.screens.comment.commentListingNavGraph
import com.innov.wakasinglebase.screens.competition.joinCompetition.joinCompetitionNavGraph
import com.innov.wakasinglebase.screens.competition.marketNavGraph
import com.innov.wakasinglebase.screens.competition.vote.voteCompetitionNavGraph
import com.innov.wakasinglebase.screens.competition.watch.watchCompetitionNavGraph
import com.innov.wakasinglebase.screens.createprofile.creatorprofile.creatorProfileNavGraph
import com.innov.wakasinglebase.screens.home.homeNavGraph
import com.innov.wakasinglebase.screens.home.video.videoNavGraph
import com.innov.wakasinglebase.screens.home.video_detail.videoDetailNavGraph
import com.innov.wakasinglebase.screens.myprofil.edit_profile.firstProfileNavGraph
import com.innov.wakasinglebase.screens.myprofil.monetisation.monetisationNavGraph
import com.innov.wakasinglebase.screens.myprofil.myProfileNavGraph
import com.innov.wakasinglebase.screens.myprofil.mybusiness.myBusinessNavGraph
import com.innov.wakasinglebase.screens.myprofil.myvideos.myVideosNavGraph
import com.innov.wakasinglebase.screens.myprofil.recharge.rechargeNavGraph
import com.innov.wakasinglebase.screens.notification.notificationNavGraph
import com.innov.wakasinglebase.signin.authNavGraph
import com.innov.wakasinglebase.signin.opt_screen.optNavGraph
import com.innov.wakasinglebase.signin.phoneScreen.phoneNavGraph
import com.innov.wakasinglebase.signin.profile_setting.profileSettingNavGraph


/**
 * Created by innov Victor on 3/14/2023.
 */

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
   // startDestination: String = DestinationRoute.AUTH_ROUTE,
    mainViewModel: MainViewModel= hiltViewModel()
) {
    val authState by mainViewModel.uiState.collectAsState()
    val context = LocalContext.current



//    var initialRoute = if(authState.loading) {
//        "loading"
////    } else if (authState.error!=null){
//        DestinationRoute.AUTH_ROUTE
//    } else if (authState.success){
//        DestinationRoute.MAIN_NAV_ROUTE
//    } else  {
//        DestinationRoute.AUTH_ROUTE
//    }
    NavHost(
        navController = navController,
        startDestination = DestinationRoute.MAIN_NAV_ROUTE,
        modifier = modifier
    ) {
        mainViewModel.onTriggerEvent(MainEvent.OnReloadUser(context))
        navigation(
            startDestination = DestinationRoute.HOME_SCREEN_ROUTE,
            route = DestinationRoute.MAIN_NAV_ROUTE
        ) {
            homeNavGraph(navController,authState)
            cameraMediaNavGraph(navController,authState)
            creatorProfileNavGraph(navController)
            watchCompetitionNavGraph(navController)
            marketNavGraph(navController,authState)
            joinCompetitionNavGraph(navController)
            uploadNavGraph(navController)
            voteCompetitionNavGraph(navController,authState)
            videoNavGraph(navController)
            publicationNavGraph(navController)
            myProfileNavGraph(navController,authState)
            firstProfileNavGraph(navController)
            commentListingNavGraph(navController)
            rechargeNavGraph(navController)
            notificationNavGraph(navController,authState)
            videoDetailNavGraph(navController)
            myVideosNavGraph(navController)
            myBusinessNavGraph(navController)
            monetisationNavGraph(navController)

        }
        navigation(DestinationRoute.AUTHENTICATION_ROUTE, DestinationRoute.AUTH_ROUTE) {
            //welcome,phone,code,Profile
            authNavGraph(navController,authState)
            phoneNavGraph(navController)
            optNavGraph(navController)
            profileSettingNavGraph(navController)
        }

        navigation(startDestination = DestinationRoute.LOADING_SCREEN, "loading") {
            composable(route = DestinationRoute.LOADING_SCREEN) {
                LoadingScreen(message = "loading")
            }
        }

    }
}

//pwd-aws=Waka@23$00_min
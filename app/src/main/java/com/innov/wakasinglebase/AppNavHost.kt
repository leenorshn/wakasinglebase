package com.innov.wakasinglebase

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.innov.wakasinglebase.screens.community.communityNavGraph
import com.innov.wakasinglebase.core.DestinationRoute.HOME_SCREEN_ROUTE
import com.innov.wakasinglebase.screens.camera.cameraMediaNavGraph
import com.innov.wakasinglebase.screens.camera.uploadNavGraph
import com.innov.wakasinglebase.screens.createprofile.creatorprofile.creatorProfileNavGraph
import com.innov.wakasinglebase.screens.explore.followingNavGraph
import com.innov.wakasinglebase.screens.home.homeNavGraph
import com.innov.wakasinglebase.screens.myprofil.myProfileNavGraph
import com.innov.wakasinglebase.signin.MainViewModel
import com.innov.wakasinglebase.signin.authNavGraph


/**
 * Created by innov Victor on 3/14/2023.
 */

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_SCREEN_ROUTE,

) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        homeNavGraph(navController)
        cameraMediaNavGraph(navController)
        creatorProfileNavGraph(navController)
        communityNavGraph(navController)
        uploadNavGraph(navController)
        followingNavGraph(navController)
        myProfileNavGraph(navController)
    }
}
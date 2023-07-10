package com.innov.wakasinglebase

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
//import com.innov.wakasinglebase.screens.camera.uploadVideoNavGraph
import com.innov.wakasinglebase.screens.community.communityNavGraph
import com.innov.wakasinglebase.core.DestinationRoute.HOME_SCREEN_ROUTE
import com.innov.wakasinglebase.screens.camera.cameraMediaNavGraph
import com.innov.wakasinglebase.screens.createprofile.creatorprofile.creatorProfileNavGraph

import com.innov.wakasinglebase.screens.explore.followingNavGraph

import com.innov.wakasinglebase.screens.home.homeNavGraph
import com.innov.wakasinglebase.screens.loginwithemailandphone.loginEmailPhoneNavGraph
import com.innov.wakasinglebase.screens.myprofil.myProfileNavGraph
import com.innov.wakasinglebase.screens.settings.settingNavGraph

/**
 * Created by innov Victor on 3/14/2023.
 */

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_SCREEN_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        homeNavGraph(navController)
        cameraMediaNavGraph(navController)
        //commentListingNavGraph(navController)
        creatorProfileNavGraph(navController)
        communityNavGraph(navController)

        loginEmailPhoneNavGraph(navController)
        followingNavGraph(navController)
        myProfileNavGraph(navController)
        settingNavGraph(navController)

    }
}
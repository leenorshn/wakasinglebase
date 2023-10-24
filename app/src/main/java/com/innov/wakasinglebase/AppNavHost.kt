package com.innov.wakasinglebase

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.innov.wakasinglebase.screens.createprofile.creatorprofile.creatorProfileNavGraph
import com.innov.wakasinglebase.screens.gift.communityNavGraph
import com.innov.wakasinglebase.screens.home.homeNavGraph
import com.innov.wakasinglebase.screens.myprofil.myProfileNavGraph
import com.innov.wakasinglebase.signin.authNavGraph
import com.innov.wakasinglebase.signin.name_and_avatar.firstProfileNavGraph
import com.innov.wakasinglebase.signin.opt_screen.optNavGraph
import com.innov.wakasinglebase.signin.phoneScreen.phoneNavGraph


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



    var initialRoute = if(authState.loading) {

        "loading"
    } else if (authState.error!=null){
        DestinationRoute.AUTH_ROUTE
    } else if (authState.success){
        DestinationRoute.MAIN_NAV_ROUTE
    } else  {
        DestinationRoute.AUTH_ROUTE
    }
    NavHost(
        navController = navController,
        startDestination = initialRoute,
        modifier = modifier
    ) {

       navigation(startDestination=DestinationRoute.HOME_SCREEN_ROUTE,route=DestinationRoute.MAIN_NAV_ROUTE){
           homeNavGraph(navController)
           cameraMediaNavGraph(navController)
           creatorProfileNavGraph(navController)
           communityNavGraph(navController)
           uploadNavGraph(navController)
           publicationNavGraph(navController)
           myProfileNavGraph(navController)
       }
        navigation(DestinationRoute.AUTHENTICATION_ROUTE,DestinationRoute.AUTH_ROUTE){
            //welcome,phone,code,Profile
            authNavGraph(navController)
            phoneNavGraph(navController)
            optNavGraph(navController)
            firstProfileNavGraph(navController)
        }

        navigation(startDestination = DestinationRoute.LOADING_SCREEN, "loading") {
            composable(route = DestinationRoute.LOADING_SCREEN) {
                LoadingScreen(message = "loading")
            }
        }

    }
}
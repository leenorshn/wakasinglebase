package com.innov.wakasinglebase



import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.innov.wakasinglebase.core.DestinationRoute.CAMERA_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.COMMENT_BOTTOM_SHEET_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.FORMATTED_COMPLETE_CREATOR_VIDEO_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.HOME_SCREEN_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.LOTTO_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.MY_PROFILE_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.NOTIFICATION_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.VIDEO_DETAIL_ROUTE
import com.innov.wakasinglebase.ui.theme.WakabaseTheme



/**
 * Created by innov Victor on 3/14/2023.
 */
@RequiresApi(Build.VERSION_CODES.P)
@OptIn(
     ExperimentalMaterialNavigationApi::class, ExperimentalMaterialApi::class
)
@Composable
fun RootScreen() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntryAsState?.destination
    val context= LocalContext.current


    val isShowBottomBar = when (currentDestination?.route) {
        HOME_SCREEN_ROUTE, LOTTO_ROUTE, COMMENT_BOTTOM_SHEET_ROUTE,
        NOTIFICATION_ROUTE, MY_PROFILE_ROUTE, null -> true
        else -> false
    }
    val darkMode = when (currentDestination?.route) {
        HOME_SCREEN_ROUTE, FORMATTED_COMPLETE_CREATOR_VIDEO_ROUTE, CAMERA_ROUTE,
             VIDEO_DETAIL_ROUTE, null -> true
        else -> false
    }

    if(currentDestination?.route== HOME_SCREEN_ROUTE){
       BackHandler {
           (context as? Activity)?.finish()
       }
    }

    WakabaseTheme(darkTheme = darkMode) {
        SetupSystemUi(rememberSystemUiController(), MaterialTheme.colorScheme.background)
       ModalBottomSheetLayout(bottomSheetNavigator = bottomSheetNavigator,) {
            Scaffold(
                topBar = {

                },
                bottomBar = {
                    if (!isShowBottomBar) {
                        return@Scaffold
                    }
                    BottomBar(navController, currentDestination, isDarkTheme = darkMode)
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    AppNavHost(navController = navController)
                }
            }
        }


    }
}

@Composable
fun SetupSystemUi(
    systemUiController: SystemUiController,
    systemBarColor: Color
) {
    SideEffect {
        systemUiController.setSystemBarsColor(color = systemBarColor)
    }
}

@ExperimentalMaterialNavigationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberBottomSheetNavigator(
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
): BottomSheetNavigator {
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        animationSpec,
    )
    return remember(sheetState) {
        BottomSheetNavigator(sheetState = sheetState)
    }
}
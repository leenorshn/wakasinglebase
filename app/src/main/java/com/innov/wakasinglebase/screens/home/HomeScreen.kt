package com.innov.wakasinglebase.screens.home


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.innov.wakasinglebase.AuthState
import com.innov.wakasinglebase.screens.home.foryou.ForYouTabScreen
import com.innov.wakasinglebase.ui.theme.WakabaseTheme


/**
 * Created by innov Victor on 3/14/2023.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    authState: AuthState
) {



    WakabaseTheme(darkTheme = true) {
        Box(modifier = Modifier.fillMaxSize()) {
            ForYouTabScreen(navController,authState)
        }
    }


}


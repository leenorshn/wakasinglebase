package com.innov.wakasinglebase.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.navigation.NavController


import com.innov.wakasinglebase.ui.theme.WakabaseTheme
import com.innov.wakasinglebase.screens.home.foryou.ForYouTabScreen


/**
 * Created by innov Victor on 3/14/2023.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController
) {



    WakabaseTheme(darkTheme = true) {
        Box(modifier = Modifier.fillMaxSize()) {
            ForYouTabScreen(navController)
        }
    }


}


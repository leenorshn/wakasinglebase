package com.innov.wakasinglebase.screens.camera


import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.core.extension.getCurrentBrightness
import com.innov.wakasinglebase.core.utils.DisableRippleInteractionSource
import com.innov.wakasinglebase.screens.camera.tabs.CameraScreen
import com.innov.wakasinglebase.ui.theme.White
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CameraMediaScreen(
    navController: NavController,
    cameraMediaViewModel: CameraMediaViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabs = Tabs.values().asList()
    val context = LocalContext.current
    val minimumScreenBrightness = 0.25f

    DisposableEffect(key1 = Unit) {
        val attrs = (context as Activity).window.attributes.apply {
            if (context.getCurrentBrightness() < minimumScreenBrightness) {
                screenBrightness = minimumScreenBrightness
            }
        }
        context.window.attributes = attrs
        onDispose {
            context.window.attributes = attrs.apply {
                screenBrightness = context.getCurrentBrightness()
            }
        }
    }


    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            Box(modifier = Modifier.weight(1f)) {
                HorizontalPager(
                    pageCount = tabs.size,
                    state = pagerState,
                    userScrollEnabled = false
                ) { page ->
                    when (page) {
                        0, 1 -> CameraScreen(
                            navController = navController,
                            viewModel = cameraMediaViewModel,
                            cameraOpenType = tabs[page]
                        )
                        2 -> CameraScreen(
                            navController = navController,
                            viewModel = cameraMediaViewModel,
                        )
                    }
                }
            }




        }
    }
}









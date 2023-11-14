package com.innov.wakasinglebase

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {



        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent {

            RootScreen()

        }
        //
    }

    override fun onDestroy() {
        super.onDestroy()




    }

}


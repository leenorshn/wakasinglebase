package com.innov.wakasinglebase

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by innov  on 3/14/2023.
 */
@HiltAndroidApp
class WakaAPP : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
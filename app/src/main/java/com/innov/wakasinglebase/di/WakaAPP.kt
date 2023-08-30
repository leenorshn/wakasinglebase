package com.innov.wakasinglebase.di

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by innov  on 3/14/2023.
 */
@HiltAndroidApp
class WakaAPP : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(applicationContext)
            Log.i("ORIO","Amplify initialised with success")
        }catch (error: AmplifyException){
            Log.e("ORIO"," $error ")
        }
    }
}
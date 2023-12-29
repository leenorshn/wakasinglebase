package com.innov.wakasinglebase.screens.camera

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.innov.wakasinglebase.AuthState
import com.innov.wakasinglebase.core.DestinationRoute

//import com.innov.wakasinglebase.signin.AuthenticationScreen


/**
 * Created by innov Victor on 4/2/2023.
 */
@RequiresApi(Build.VERSION_CODES.P)
fun NavGraphBuilder.cameraMediaNavGraph(navController: NavController,authState: AuthState) {
//    val viewModel=SignInViewModel()
//    val state=viewModel.checkUser()
////   if(){
       composable(route = DestinationRoute.CAMERA_ROUTE) {

              CameraMediaScreen(navController,authState)


       }




}
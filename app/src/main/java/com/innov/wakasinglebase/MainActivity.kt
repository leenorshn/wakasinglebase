package com.innov.wakasinglebase

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.SignInClient

import com.innov.wakasinglebase.signin.MainViewModel
import com.innov.wakasinglebase.signin.SignInScreen

import com.innov.wakasinglebase.signin.utils.GoogleAuthUiHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var googleAuthUiHelper: GoogleAuthUiHelper
    @Inject lateinit var oneTapClient : SignInClient



    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
               // RootScreen()

            val navController = rememberNavController()
            val viewModel : MainViewModel = hiltViewModel()

            val uiState = viewModel.uiState.value

            NavHost(navController = navController, startDestination = "signin"){
                composable("signin"){

                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = {result->
                            if(result.resultCode == RESULT_OK){
                                lifecycleScope.launch {
                                    val signInResult = googleAuthUiHelper.getSignInResultFromIntentAndSignIn(
                                        intent = result.data ?: return@launch
                                    )
                                    viewModel.onSignInResult(signInResult)
                                }
                            }
                        }
                    )

                    LaunchedEffect(key1 = uiState.currentUser){
                        Log.e("Magi","${uiState.currentUser?.uid}")

                        if(uiState.currentUser != null){
                            navController.navigate("main")
                        }
                    }

                    LaunchedEffect(key1 = uiState.isSignInSuccessfull ){
                        if(uiState.isSignInSuccessfull){
                            navController.navigate("main")
                            //viewModel.resetSignInState()
                        }
                    }

                    SignInScreen(
                        isLoading = uiState.isLoading,
                        currentUser = uiState.currentUser,
                        error = uiState.signinError?:"Erreur inconnue",
                        onSignInClick = {
                            lifecycleScope.launch {
                                val signInIntentSender = googleAuthUiHelper.actionSignIn()
                                launcher.launch(
                                    IntentSenderRequest.Builder(
                                        signInIntentSender ?: return@launch
                                    ).build()
                                )
                            }
                        }
                    )
                }
                composable("main"){
                    RootScreen()
                }





            }

        }
    }


}


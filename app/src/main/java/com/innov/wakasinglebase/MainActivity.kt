package com.innov.wakasinglebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.innov.wakasinglebase.signin.AuthenticationScreen
import com.innov.wakasinglebase.signin.GoogleAuthUiClient
import com.innov.wakasinglebase.signin.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
   // private val authViewModel: AuthViewModel by viewModels()

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        super.onCreate(savedInstanceState)


        setContent {

            val viewModel= viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val navController= rememberNavController()
            NavHost(navController = navController, startDestination = "sign_in"){

                composable("sign_in"){


                    val launcher= rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult ={result->
                            if (result.resultCode== RESULT_OK){
                                lifecycleScope.launch {
                                    val signInResult=googleAuthUiClient.signInWithIntent(result.data ?: return@launch)
                                    viewModel.onSignInResult(signInResult)
                                }
                            }
                        }
                    )
                    LaunchedEffect(key1 = state.isSignSuccessFul,  ){
                        if (state.isSignSuccessFul){
                            Toast.makeText(applicationContext,"Success logged in",Toast.LENGTH_LONG)
                                .show()

                        navController.navigate("root_screen")
                        viewModel.resetState()
                        }
                    }
                    
                    AuthenticationScreen(state = state,
                    onClickButton = {
                        lifecycleScope.launch {
                            val signInIntentSender=googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    })
                }
                composable("root_screen"){
                    RootScreen()
                }
            }
        }
    }
}


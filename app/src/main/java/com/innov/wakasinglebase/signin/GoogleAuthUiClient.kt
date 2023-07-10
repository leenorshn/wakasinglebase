package com.innov.wakasinglebase.signin

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUiClient(
    private val context:Context,
    private val oneTapClient:SignInClient
) {

    private  val auth= Firebase.auth

    suspend fun  signIn():IntentSender?{
        val result=try{
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        }catch (e:Exception){
            e.printStackTrace()
            if (e is CancellationException) throw  e
            null
        }

        return  result?.pendingIntent?.intentSender
    }

    suspend fun  signInWithIntent(intent:Intent): SignInResult {
        val credential=oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIDToken=credential.googleIdToken
        val googleCredentials=GoogleAuthProvider.getCredential(googleIDToken,null)

        return  try {

            val userCred= auth.signInWithCredential(googleCredentials).await()
            if(userCred.additionalUserInfo?.isNewUser==true){
                // ecrire dans la base de donnes.
            }
            SignInResult(
                data = userCred?.user?.run {
                    AuthData(
                        uid = uid,
                        userName = displayName,
                        profilePictureUrl = photoUrl.toString()
                    )
                },
                errorMessage=null
            )


        }catch (e:Exception){
            e.printStackTrace()
            if (e is CancellationException) throw  e
            SignInResult(
                data =null,
                errorMessage = e.message
            )
        }
    }

    suspend fun signOut(){
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        }catch (e:Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    fun getSignInUser(): AuthData?=auth.currentUser?.run{
        AuthData(
            uid =uid,
            userName = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    private  fun buildSignInRequest():BeginSignInRequest{
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId("907204338966-s4ncscdf88rofqr84kjvhvevfh8uso22.apps.googleusercontent.com")
                    .build()
            ).setAutoSelectEnabled(true)
            .build()
    }
}
package com.innov.wakasinglebase.signin.utils



import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.signin.AuthData
import com.innov.wakasinglebase.signin.SignInResult
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUiHelper(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth
    private  val db=FirebaseFirestore.getInstance()

    suspend fun actionSignIn() : IntentSender?{
        val result = try {
            oneTapClient.beginSignIn(
                BeginSignInRequest.builder()
                    .setGoogleIdTokenRequestOptions(
                        GoogleIdTokenRequestOptions.builder()
                            .setSupported(true)
                            .setFilterByAuthorizedAccounts(false)
                            .setServerClientId(context.getString(R.string.default_web_client_id))
                            .build()
                    )
                    .setAutoSelectEnabled(true)
                    .build()
            ).await()
        }catch (e : Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun getSignInResultFromIntentAndSignIn(intent: Intent) : SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(googleIdToken,null)
        return try {
            val cred= auth.signInWithCredential(googleCredential).await().additionalUserInfo

            cred.let {
                val user = auth.signInWithCredential(googleCredential).await().user
                if (it?.isNewUser==true){
                    // create user in firestore
                    saveCurrentUser(
                        uid =user?.uid?:"",
                        name = user?.displayName?:"",
                        email = user?.email?:"",
                        profilePic = user?.photoUrl.toString()?:""
                    )
                }
            }


            val user = auth.signInWithCredential(googleCredential).await().user

            SignInResult(
                data = user?.let { getCurrentUser(it.uid) },
                errorMessage = null
            )
        }catch (e : Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(null, e.message)
        }
    }


   private suspend fun saveCurrentUser(uid: String, name:String, email:String, profilePic:String){
        db.collection("users").document(uid).set(
          UserModel(
              uid=uid,
              name=name,
              email = email,
              phone = "",
              hasContract = false,
              balance = 0,
              profilePic = profilePic,
              isVerified = true,
              uniqueUserName = name.replace(" ","_"),
              bio = "",
              city = ""
          )
        ).await()
    }
}

suspend fun getCurrentUser(uid:String):UserModel?{
    return  FirebaseFirestore
        .getInstance()
        .collection("users")
        .document(uid)
        .get().await()
        .toObject(UserModel::class.java)
}
package com.innov.wakasinglebase.data.repository.authentification

import android.util.Log
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.signin.AuthData
import com.innov.wakasinglebase.signin.Result.Error
import com.innov.wakasinglebase.signin.Result.Loading
import com.innov.wakasinglebase.signin.Result.Success
import com.innov.wakasinglebase.signin.utils.getCurrentUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
){


    suspend fun signOut(oneTapClient : SignInClient) = flow {
        emit(Loading)
        oneTapClient.signOut().await()
        Firebase.auth.signOut()
        emit(Success(true))
    }.catch { error->
        emit(Error(error))
    }

    fun getSignedInUser()= flow {
        emit(Loading)
        val fcu = auth.currentUser
        val user = if(fcu!=null){
            //Log.e("AUTH","************${t?.name}***************")
            var useer:UserModel?
           getCurrentUser(uid=fcu.uid).let {
               useer=it
           }

            useer
        }else null

        emit(Success(user))
    }.catch { error->
        emit(Error(error))
    }
}
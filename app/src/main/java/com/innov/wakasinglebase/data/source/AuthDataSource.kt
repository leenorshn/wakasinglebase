package com.innov.wakasinglebase.data.source

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.innov.wakasinglebase.data.model.UserModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await

class AuthDataSource {
    private val db = Firebase.firestore
    private val auth=Firebase.auth
    suspend fun createUser(name: String,email:String,uid:String,profilePic:String){
// Create a new user with a first and last name
        val user = hashMapOf(
            "name" to name,
            "uniqueUserName" to name.replace(" ","_"),
            "email" to email,
            "uid" to uid,
            "profilePic" to profilePic,
            "phone" to "not define",
            "bio" to "not define",
            "balance" to 0,
            "city" to "No define",
            "isVerified" to false,
            "hasContract" to false,
        )

// Add a new document with a generated ID
        db.collection("users")
            .document(uid)
            .set(user).await()
    }
      fun getUser(){
       auth.currentUser?.let {user->
        val reff=  db.collection("users")
               .document(user.uid).get()
           reff.addOnSuccessListener {
               it.toObject<UserModel>()
           }
       }
    }
}
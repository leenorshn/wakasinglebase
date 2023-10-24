package com.innov.wakasinglebase.data.source

import com.innov.wakasinglebase.data.model.UserModel


class AuthDataSource {


    suspend fun createUser(name: String, email: String, uid: String, profilePic: String) {
// Create a new user with a first and last name
        val user = hashMapOf(
            "name" to name,
            "uniqueUserName" to name.replace(" ", "_"),
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
//        db.collection("users")
//            .document(uid)
//            .set(user).await()
    }

    fun getUser(): UserModel? {
        var userDb: UserModel? = null
//       auth.currentUser?.let {user->
//        userDb= db.collection("users")
//               .document(user.uid).get().result
//
//            .toObject<UserModel>()

        //  }
        return userDb
    }

}
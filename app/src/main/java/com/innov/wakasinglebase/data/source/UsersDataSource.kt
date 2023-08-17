package com.innov.wakasinglebase.data.source


import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.innov.wakasinglebase.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

/**
 * Created by innov Victor on 3/18/2023.
 */
object UsersDataSource {

    suspend fun fetchSpecificUser(userId: String): Flow<UserModel?> {
        var db=FirebaseFirestore.getInstance();
        Log.e("Waka",userId)
        val res=db.collection("users").document(userId).get().await()
        return flow {
            val user = UserModel(
                uid = userId,
                name = res.data?.get("name").toString(),
                phone = res.data?.get("phone").toString(),
                uniqueUserName = res.data?.get("name").toString().replace(" ","_"),
                bio = res.data?.get("bio").toString(),
                city = res.data?.get("city").toString(),
                email = res.data?.get("email").toString(),
                profilePic = res.data?.get("profilePic").toString()
            )
            emit(user)
        }
    }

}



package com.innov.wakasinglebase.data.source


import com.innov.wakasinglebase.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by innov Victor on 3/18/2023.
 */
object UsersDataSource {
    val kylieJenner = UserModel(
        uid = "keyliejenner",
        uniqueUserName = "keyliejenner",
        name = "Kylie jenner",
        bio = "Kylie",
        profilePic = "https://c4.wallpaperflare.com/wallpaper/887/659/808/kylie-jenner-2018-wallpaper-preview.jpg",
        isVerified = true,
        city = "Butembo",
        email = "shukurukate@gmail.com",
        hasContract = true,
        phone = "+243978154329"

    )

















    val userList = listOf(
        kylieJenner,

    )

    fun fetchSpecificUser(userId: String): Flow<UserModel?> {
        return flow {
            val user = userList.firstOrNull { it.uid == userId }
            emit(user)
        }
    }

}



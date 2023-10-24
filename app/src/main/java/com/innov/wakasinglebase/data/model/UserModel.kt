package com.innov.wakasinglebase.data.model



/**
 * Created by innov  on 3/18/2023.
 */
data class UserModel(
    val uid: String?="",
    val uniqueUserName: String?="",
    val name: String?="",
    val email:String?="",
    val city:String?="",
    val phone:String?="",
    val bio: String?="",
    val balance: Double =0.0,
    val profilePic: String?="",
    val isVerified: Boolean=false,
    val hasContract: Boolean = false,
)

data class AuthModel(
    val token:String=""
)




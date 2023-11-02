package com.innov.wakasinglebase.data.model



/**
 * Created by innov  on 3/18/2023.
 */
data class UserModel(
    val uid: String ?=null,
    val uniqueUserName: String?=null,
    val name: String?=null,
    val email:String?=null,
    val city:String?=null,
    val phone:String="",
    val bio: String?=null,
    val balance: Double =0.0,
    val profilePic: String?=null,
    val isVerified: Boolean=false,
    val hasContract: Boolean = false,
)

data class AuthModel(
    val token:String=""
)




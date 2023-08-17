package com.innov.wakasinglebase.signin

import com.innov.wakasinglebase.data.model.UserModel


data class SignInResult(
    val data: UserModel?,
    val errorMessage:String?
)

data class AuthData(
        val uid:String,
        val userName:String?,
        val profilePictureUrl:String?
)

package com.innov.wakasinglebase.signin


data class SignInResult(
    val data: AuthData?,
    val errorMessage:String?
)

data class AuthData(
        val uid:String,
        val userName:String?,
        val profilePictureUrl:String?
)

package com.innov.wakasinglebase.signin


import com.innov.wakasinglebase.data.model.UserModel

data class SignInState(
    val isSignSuccessFul:Boolean=false,
val signInError:String?=null
)

sealed class Result<T>(val data : T? = null, val e : Throwable? = null){
    object Loading : Result<Nothing>()
    class Success<T>(data: T?) : Result<T>(data)
    class Error<T>(e: Throwable) : Result<T>(e= e)
}

data class UiState(
    val isSignInSuccessfull : Boolean = false,
    val signinError : String? = null,
    val currentUser: UserModel? = null,
    val isLoading : Boolean = false,

)

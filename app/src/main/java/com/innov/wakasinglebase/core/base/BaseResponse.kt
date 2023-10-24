package com.innov.wakasinglebase.core.base

sealed class BaseResponse<out T> {
    data class Success<T>(val data:T):BaseResponse<T>()
    data class Error(val error:String):BaseResponse<Nothing>()
    object Loading:BaseResponse<Nothing>()
}

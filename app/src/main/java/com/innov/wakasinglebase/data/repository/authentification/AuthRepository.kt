package com.innov.wakasinglebase.data.repository.authentification

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.AuthModel
import com.innov.wakasinglebase.data.model.UserModel

import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    suspend fun verifyPhone(phone:String): Flow<BaseResponse<Boolean>>
    suspend fun verifyCode(phone: String,code:String):Flow<BaseResponse<AuthModel?>>

    suspend fun updateUserData(name:String,avatar:String):Flow<BaseResponse<Boolean>>
    suspend fun me():Flow<BaseResponse<UserModel?>>
}
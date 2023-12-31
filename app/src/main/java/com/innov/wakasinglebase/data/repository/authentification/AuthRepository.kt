package com.innov.wakasinglebase.data.repository.authentification

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.AuthModel
import com.innov.wakasinglebase.data.model.UserModel
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    suspend fun verifyPhone(phone:String): Flow<BaseResponse<Boolean>>
    suspend fun verifyCode(phone: String,code:String):Flow<BaseResponse<AuthModel?>>

    suspend fun updateUserData(name:String?,avatar:String?,bio:String?):Flow<BaseResponse<Boolean>>
    suspend fun me():Flow<BaseResponse<UserModel?>>

    suspend fun friends(id:String):Flow<BaseResponse<List<UserModel>>>

    suspend fun myFriends():Flow<BaseResponse<List<UserModel>>>
    suspend fun users():Flow<BaseResponse<List<UserModel>>>

    suspend fun follow(id:String):Flow<BaseResponse<Boolean>>

    suspend fun unFollow(id:String):Flow<BaseResponse<Boolean>>

    suspend fun askMonetisation():Flow<BaseResponse<Boolean>>

    suspend fun  updateUserOnlineState(state:Boolean):Flow<BaseResponse<Boolean>>

}
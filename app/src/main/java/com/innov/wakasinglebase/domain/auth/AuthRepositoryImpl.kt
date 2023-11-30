package com.innov.wakasinglebase.domain.auth

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.AuthModel
import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.repository.authentification.AuthRepository
import com.innov.wakasinglebase.data.source.UserDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
   private val userDataSource: UserDataSource
):AuthRepository{
    override suspend fun verifyPhone(phone: String): Flow<BaseResponse<Boolean>> {
        return userDataSource.verifyPhone(phone)
    }

    override suspend fun verifyCode(phone: String, code: String): Flow<BaseResponse<AuthModel?>> {
        return userDataSource.verifyCode(phone,code)
    }

    override suspend fun updateUserData(name: String?, avatar: String?,bio:String?): Flow<BaseResponse<Boolean>> {
        return userDataSource.updateUserData(name,avatar,bio)
    }

    override suspend fun me(): Flow<BaseResponse<UserModel?>> {
        return userDataSource.me()
    }

    override suspend fun friends(id: String): Flow<BaseResponse<List<UserModel>>> {
        return userDataSource.fetchFriendUser(id)
    }

    override suspend fun myFriends(): Flow<BaseResponse<List<UserModel>>> {
        return userDataSource.fetchMyFriend()
    }


    override suspend fun users(): Flow<BaseResponse<List<UserModel>>> {
        return  userDataSource.getUsers()
    }

    override suspend fun follow(id: String): Flow<BaseResponse<Boolean>> {
        return userDataSource.followMe(id)
    }

    override suspend fun unFollow(id: String): Flow<BaseResponse<Boolean>> {
        return userDataSource.unFollowMe(id)
    }

    override suspend fun askMonetisation(): Flow<BaseResponse<Boolean>> {
        return userDataSource.demandeMonetisation()
    }

    override suspend fun updateUserOnlineState(state: Boolean): Flow<BaseResponse<Boolean>> {
        return userDataSource.updateUserOnline(state)
    }
}
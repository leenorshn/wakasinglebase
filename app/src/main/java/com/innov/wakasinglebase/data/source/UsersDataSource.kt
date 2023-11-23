package com.innov.wakasinglebase.data.source


import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.mapper.toAuthModel
import com.innov.wakasinglebase.data.mapper.toUserModel
import com.innov.wakasinglebase.data.model.AuthModel
import com.innov.wakasinglebase.data.model.UserModel
import com.wakabase.FollowMeMutation
import com.wakabase.FriendsQuery
import com.wakabase.LoginOrCreateAccountMutation
import com.wakabase.MeQuery
import com.wakabase.MyFriendsQuery
import com.wakabase.UnFollowMeMutation
import com.wakabase.UpdateUserMutation
import com.wakabase.UpdateUserOnlineMutation
import com.wakabase.UserQuery
import com.wakabase.UsersQuery
import com.wakabase.VerifyPhoneMutation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by innov Victor on 3/18/2023.
 */
class UserDataSource @Inject constructor(
   private val apolloClient: ApolloClient
) {

//    suspend fun fetchSpecificUser(userId: String): Flow<UserModel?> {
//
//
//        return flow {
//            val user = UserModel(
//                uid = userId,
//            )
//            emit(user)
//        }
//    }

    suspend fun updateUserOnline(state:Boolean):Flow<BaseResponse<Boolean>>{
        return flow {
            emit(BaseResponse.Loading)
            val res= apolloClient.mutation(UpdateUserOnlineMutation(state)).execute()
            if(res.data?.updateUserOnlineState!=null){
                emit(BaseResponse.Success(true))
            }
        }.catch {
            emit(BaseResponse.Error("Error of network"))
        }
    }

    suspend fun verifyPhone(phone: String): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            //emit(true)
            var res=apolloClient.mutation(VerifyPhoneMutation(phone)).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("error ${res.errors?.joinToString ()}"))
            }
            if (res.data?.sendCode==true){
                emit(BaseResponse.Success(true))
            }
        }.catch {

            emit(BaseResponse.Error("error ${it.message}"))
        }
    }
    suspend fun verifyCode(phone:String,code:String): Flow<BaseResponse<AuthModel?>> {
        return flow {
            emit(BaseResponse.Loading)
            //emit(true)
            val res=apolloClient.mutation(LoginOrCreateAccountMutation(phone,code)).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("code error in verification"))
            }
            if (res.data?.loginOrCreateAccount!=null){
                val data=res.data?.loginOrCreateAccount?.toAuthModel()
                emit(BaseResponse.Success(data))
            }
        }.catch {
            emit(BaseResponse.Error("code error in verification"))
        }
    }
    suspend fun me(): Flow<BaseResponse<UserModel?>> {
        return flow {
            emit(BaseResponse.Loading)

          val res= apolloClient.query(MeQuery())
              .fetchPolicy(FetchPolicy.NetworkFirst)
              .execute()

            if (res.hasErrors()){
                emit(BaseResponse.Error("error hard to load current user"))
            }
            if(res.data!=null){
                val user=res.data?.me?.toUserModel()
                emit(BaseResponse.Success(user))
            }
        }.catch {
            emit(BaseResponse.Error("error user login"))
        }
    }

    suspend fun updateUserData(name:String?,avatar:String?,bio:String?):Flow<BaseResponse<Boolean>>{
        return flow {
            emit(BaseResponse.Loading)
            val res=apolloClient.mutation(UpdateUserMutation(name,avatar,bio)).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error when updating profile ${res.errors?.joinToString() }"))
            }
            if (res.data!=null){
                emit(BaseResponse.Success(true))
            }
        }.catch {
            emit(BaseResponse.Error("Error when updating profile"))
        }
    }



    suspend fun getUsers(): Flow<BaseResponse<List<UserModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            val res=apolloClient.query(UsersQuery()).fetchPolicy(FetchPolicy.NetworkFirst).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error when updating profile ${res.errors?.joinToString() }"))
            }
            val users=   res.data?.users?.map {
                it.toUserModel()
            }?:emptyList()
            emit(BaseResponse.Success(users))
        }.catch {
            emit(BaseResponse.Error("Error when updating profile"))
        }
    }

    suspend fun followMe(id:String):Flow<BaseResponse<Boolean>>{
        return flow {
            emit(BaseResponse.Loading)
            val res=apolloClient.mutation(FollowMeMutation(id)).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("error"))
            }
            emit(BaseResponse.Success(true))
        }.catch {
            emit(BaseResponse.Error("error"))
        }
    }
    suspend fun unFollowMe(id:String):Flow<BaseResponse<Boolean>>{
        return flow {
            emit(BaseResponse.Loading)
            val res=apolloClient.mutation(UnFollowMeMutation(id)).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("error"))
            }
            emit(BaseResponse.Success(true))
        }.catch {
            emit(BaseResponse.Error("error"))
        }
    }

     fun fetchSpecificUser(id: String): Flow<BaseResponse<UserModel>> {
        return flow {
            emit(BaseResponse.Loading)

            val res=apolloClient.query(UserQuery(id)).fetchPolicy(FetchPolicy.NetworkFirst).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error when updating profile ${res.errors?.joinToString() }"))
            }
            val user=   res.data?.user?.toUserModel()
            emit(BaseResponse.Success(user!!))
        }.catch {
            emit(BaseResponse.Error("error ${it.message}"))
        }
    }

    fun fetchFriendUser(id: String): Flow<BaseResponse<List<UserModel>>> {
        return flow {
            emit(BaseResponse.Loading)

            val res=apolloClient.query(FriendsQuery(id)).fetchPolicy(FetchPolicy.NetworkFirst).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error when updating profile ${res.errors?.joinToString() }"))
            }
            val users=   res.data?.friends?.map { it.toUserModel() }?: emptyList()
            emit(BaseResponse.Success(users))
        }.catch {
            emit(BaseResponse.Error("error ${it.message}"))
        }
    }

    fun fetchMyFriend(): Flow<BaseResponse<List<UserModel>>> {
        return flow {
            emit(BaseResponse.Loading)

            val res=apolloClient.query(MyFriendsQuery()).fetchPolicy(FetchPolicy.NetworkFirst).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error when updating profile ${res.errors?.joinToString() }"))
            }
            val users=   res.data?.myFriends?.map { it.toUserModel() }?: emptyList()
            emit(BaseResponse.Success(users))
        }.catch {
            emit(BaseResponse.Error("error ${it.message}"))
        }
    }


}



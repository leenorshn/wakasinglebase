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
import com.wakabase.MonetisateMyAccountMutation
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

    suspend fun demandeMonetisation(): Flow<BaseResponse<Boolean>> {


        return flow {
            emit(BaseResponse.Loading)
            var resp=apolloClient.mutation(
                MonetisateMyAccountMutation()
            ).execute()

            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error check your network"))
            }
            emit(BaseResponse.Success(true))
        }
    }

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
                emit(BaseResponse.Error("error of network or bad request"))
            }
            if (res.data?.sendCode==true){
                emit(BaseResponse.Success(true))
            }
        }.catch {

            emit(BaseResponse.Error("error of network"))
        }
    }
    suspend fun verifyCode(phone:String,code:String): Flow<BaseResponse<AuthModel?>> {
        return flow {
            emit(BaseResponse.Loading)
            //emit(true)
            val res=apolloClient.mutation(LoginOrCreateAccountMutation(phone,code)).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error when verifying code"))
            }
            if (res.data?.loginOrCreateAccount!=null){
                val data=res.data?.loginOrCreateAccount?.toAuthModel()
                emit(BaseResponse.Success(data))
            }
        }.catch {
            emit(BaseResponse.Error("Error when verifying code"))
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
                emit(BaseResponse.Error("Error when updating profile "))
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
                emit(BaseResponse.Error("Error when fetching users"))
            }
            val users=   res.data?.users?.map {
                it.toUserModel()
            }?:emptyList()
            emit(BaseResponse.Success(users))
        }.catch {
            emit(BaseResponse.Error("Error when fetching users"))
        }
    }

    suspend fun followMe(id:String):Flow<BaseResponse<Boolean>>{
        return flow {
            emit(BaseResponse.Loading)
            val res=apolloClient.mutation(FollowMeMutation(id)).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("error of network"))
            }
            emit(BaseResponse.Success(true))
        }.catch {
            emit(BaseResponse.Error("error of network"))
        }
    }
    suspend fun unFollowMe(id:String):Flow<BaseResponse<Boolean>>{
        return flow {
            emit(BaseResponse.Loading)
            val res=apolloClient.mutation(UnFollowMeMutation(id)).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("error of network"))
            }
            emit(BaseResponse.Success(true))
        }.catch {
            emit(BaseResponse.Error("error of network"))
        }
    }

     fun fetchSpecificUser(id: String): Flow<BaseResponse<UserModel>> {
        return flow {
            emit(BaseResponse.Loading)

            val res=apolloClient.query(UserQuery(id)).fetchPolicy(FetchPolicy.NetworkFirst).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error user not found"))
            }
            val user=   res.data?.user?.toUserModel()
            emit(BaseResponse.Success(user!!))
        }.catch {
            emit(BaseResponse.Error("error some thing wrong happen"))
        }
    }

    fun fetchFriendUser(id: String): Flow<BaseResponse<List<UserModel>>> {
        return flow {
            emit(BaseResponse.Loading)

            val res=apolloClient.query(FriendsQuery(id)).fetchPolicy(FetchPolicy.NetworkFirst).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error users not found"))
            }
            val users=   res.data?.friends?.map { it.toUserModel() }?: emptyList()
            emit(BaseResponse.Success(users))
        }.catch {
            emit(BaseResponse.Error("Error some thing wrong happen "))
        }
    }

    fun fetchMyFriend(): Flow<BaseResponse<List<UserModel>>> {
        return flow {
            emit(BaseResponse.Loading)

            val res=apolloClient.query(MyFriendsQuery()).fetchPolicy(FetchPolicy.NetworkFirst).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error users not found }"))
            }
            val users=   res.data?.myFriends?.map { it.toUserModel() }?: emptyList()
            emit(BaseResponse.Success(users))
        }.catch {
            emit(BaseResponse.Error("Error unknown error"))
        }
    }


}



package com.innov.wakasinglebase.data.source

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.CommunityModel
import com.innov.wakasinglebase.data.model.toCommunityModel
import com.wakabase.CommunitiesQuery
import com.wakabase.CommunityQuery
import com.wakabase.CreateCommunityMutation
import com.wakabase.PublishMessageMutation
import com.wakabase.type.NewCommunity
import com.wakabase.type.NewMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommunityDataSource @Inject constructor(
    val apolloClient: ApolloClient
) {

    fun createCommunity(data:NewCommunity):Flow<BaseResponse<Boolean>>{
        return flow {
            emit(BaseResponse.Loading)
            val resp=apolloClient.mutation(CreateCommunityMutation(data)).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of creating a community"))
            }
            emit(BaseResponse.Success(true))
        }
    }

    fun publishMessage(data:NewMessage):Flow<BaseResponse<Boolean>>{
        return flow {
            emit(BaseResponse.Loading)
            val resp=apolloClient.mutation(PublishMessageMutation(data)).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of creating a community"))
            }
            emit(BaseResponse.Success(true))
        }
    }

    fun getCommunity(id:String):Flow<BaseResponse<CommunityModel?>>{
        return flow {
            emit(BaseResponse.Loading)
            val resp=apolloClient.query(CommunityQuery(id)).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of creating a community"))
            }

            val data=resp.data?.community?.toCommunityModel()
            emit(BaseResponse.Success(data))
        }
    }

    fun getCommunities():Flow<BaseResponse<List<CommunityModel>>>{
        return flow {
            emit(BaseResponse.Loading)
            val resp=apolloClient.query(CommunitiesQuery()).fetchPolicy(FetchPolicy.NetworkFirst).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of creating a community"))
            }

            val data=resp.data?.communities?.map {
                it.toCommunityModel()
            }?: emptyList()
            emit(BaseResponse.Success(data))
        }
    }
}
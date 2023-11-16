package com.innov.wakasinglebase.data.source

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.mapper.toThreadModel
import com.innov.wakasinglebase.data.model.ThreadModel
import com.wakabase.AttacheVideoToThreadMutation
import com.wakabase.CreateNewThreadMutation
import com.wakabase.MyThreadsQuery
import com.wakabase.ThreadQuery
import com.wakabase.ThreadsQuery
import com.wakabase.type.NewThread
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ThreadDataSource @Inject constructor(
    private val apolloClient: ApolloClient,

){
    fun fetchThreads(): Flow<BaseResponse<List<ThreadModel>>> {

        return flow {
            emit(BaseResponse.Loading)
            val resp= apolloClient.query(ThreadsQuery()).fetchPolicy(FetchPolicy.CacheAndNetwork).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of loading ticket check your network"))
            }
            val threads=resp.data?.threads?.map {
                it.toThreadModel()
            }?: emptyList()
            emit(BaseResponse.Success(threads))
        }
    }

    fun fetchMyThreads(): Flow<BaseResponse<List<ThreadModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            val resp= apolloClient.query(MyThreadsQuery()).fetchPolicy(FetchPolicy.CacheAndNetwork).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of loading ticket check your network"))
            }
            val threads=resp.data?.myThreads?.map {
                it.toThreadModel()
            }?: emptyList()
            emit(BaseResponse.Success(threads))
        }
    }

    fun fetchThread(id:String): Flow<BaseResponse<ThreadModel>> {
        return flow {
            emit(BaseResponse.Loading)
            val resp= apolloClient.query(ThreadQuery(id)).fetchPolicy(FetchPolicy.CacheAndNetwork).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of loading thread check your network"))
            }
            val thread=resp.data?.thread?.toThreadModel()
           if (thread!=null){
               emit(BaseResponse.Success(thread))
           }else{
               emit(BaseResponse.Error("Error thread not found"))
           }

        }
    }
    fun createThread(data:NewThread): Flow<BaseResponse<Boolean>> {

        return flow {
            emit(BaseResponse.Loading)
            val resp= apolloClient.mutation(CreateNewThreadMutation(data)).fetchPolicy(FetchPolicy.CacheAndNetwork).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of loading ticket check your network"))
            }

            emit(BaseResponse.Success(true))
        }
    }

    fun attacheThread(videoId:String,threadId:String): Flow<BaseResponse<Boolean>> {

        return flow {
            emit(BaseResponse.Loading)
            val resp= apolloClient.mutation(AttacheVideoToThreadMutation(videoId,threadId)).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of loading ticket check your network"))
            }
            emit(BaseResponse.Success(resp.data?.linkWithThread==true))
        }
    }
}
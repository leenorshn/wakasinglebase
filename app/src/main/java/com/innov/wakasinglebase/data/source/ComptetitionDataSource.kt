package com.innov.wakasinglebase.data.source

import CompetitionModel
import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.innov.wakasinglebase.core.base.BaseResponse
import com.wakabase.CompetitionQuery
import com.wakabase.CompetitionsQuery
import com.wakabase.CreateCompetitionMutation
import com.wakabase.JoinCompetitionMutation
import com.wakabase.VoteVideoMutation
import com.wakabase.type.NewCompetition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import toCompetitionModel
import javax.inject.Inject

class CompetitionDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {

    fun createCompetition(data:NewCompetition):Flow<BaseResponse<Boolean>>{
        return flow {
            emit(BaseResponse.Loading)
            var res=apolloClient.mutation(CreateCompetitionMutation(data)).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error of creating a competition"))
            }
            else{
                emit(BaseResponse.Success(true))
            }
        }

    }

    suspend fun voteVideo(
        videoId: String,
    ): Flow<BaseResponse<Boolean>> {


        return flow {
            emit(BaseResponse.Loading)

            val res = apolloClient.mutation(VoteVideoMutation(videoId)).execute()

            if (res.hasErrors()){
                emit(BaseResponse.Error("Error when liking a video"))
            }else{
                emit(BaseResponse.Success(true))
            }


        }.catch {
            emit(BaseResponse.Error("Error when creating video"))
        }
    }

    fun joinCompetition(competition:String):Flow<BaseResponse<Boolean>>{
        return flow {
            emit(BaseResponse.Loading)
            var res=apolloClient.mutation(JoinCompetitionMutation(competition,)).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error of joining a competion"))
            }
            else{
                emit(BaseResponse.Success(true))
            }
        }
    }

    fun getCompetitions():Flow<BaseResponse<List<CompetitionModel>>>{
        return flow {
            emit(BaseResponse.Loading)
            var res=apolloClient.query(CompetitionsQuery())
                .fetchPolicy(FetchPolicy.NetworkFirst).execute()
            if (res.hasErrors()){
                Log.d("COMPETITIONS",res.errors.toString())
                emit(BaseResponse.Error("Error of loading  message"))
            }
            else{
              val competitions:List<CompetitionModel> =  res.data?.allCompetitions?.map { it.toCompetitionModel() }?: emptyList()
                Log.d("COMPETITIONS",competitions.toString())
                emit(BaseResponse.Success(competitions))
            }
        }
    }

    fun getCompetition(id:String):Flow<BaseResponse<CompetitionModel?>>{
        return flow {
            emit(BaseResponse.Loading)
            var res=apolloClient.query(CompetitionQuery(id)).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error of loading  message"))
            }
            else{
                val competitions: CompetitionModel? =  res.data?.competition?.toCompetitionModel()
                emit(BaseResponse.Success(competitions))
            }
        }
    }
}
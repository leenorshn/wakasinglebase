package com.innov.wakasinglebase.data.source

import CompetitionModel
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.innov.wakasinglebase.core.base.BaseResponse
import com.wakabase.CompetitionQuery
import com.wakabase.CompetitionsQuery

import com.wakabase.CreateCompetitionMutation
import com.wakabase.JoinCompetitionMutation

import com.wakabase.type.NewCompetition
import kotlinx.coroutines.flow.Flow
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
                emit(BaseResponse.Error("Error of loading  message"))
            }
            else{
              val competitions:List<CompetitionModel> =  res.data?.allCompetitions?.map { it.toCompetitionModel() }?: emptyList()
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
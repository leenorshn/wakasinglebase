package com.innov.wakasinglebase.data.repository.competition

import CompetitionModel
import com.innov.wakasinglebase.core.base.BaseResponse
import com.wakabase.type.NewCompetition
import kotlinx.coroutines.flow.Flow

interface CompetitionRepository {

    suspend fun getCompetitions(): Flow<BaseResponse<List<CompetitionModel>>>

    suspend fun getCompetition(id:String): Flow<BaseResponse<CompetitionModel?>>
    suspend fun createCompetition(data:NewCompetition):Flow<BaseResponse<Boolean>>

    suspend fun joinCompetition(uid:String):Flow<BaseResponse<Boolean>>
    suspend fun voteVideo(id:String):Flow<BaseResponse<Boolean>>
}
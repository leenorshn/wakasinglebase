package com.innov.wakasinglebase.domain

import CompetitionModel
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.repository.competition.CompetitionRepository
import com.innov.wakasinglebase.data.source.CompetitionDataSource
import com.wakabase.type.NewCompetition
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompetitionRepositoryImpl @Inject constructor(
    private val dataSource: CompetitionDataSource
): CompetitionRepository {


    override suspend fun getCompetitions(): Flow<BaseResponse<List<CompetitionModel>>> {
        return dataSource.getCompetitions()
    }

    override suspend fun getCompetition(id: String): Flow<BaseResponse<CompetitionModel?>> {
        return dataSource.getCompetition(id)
    }

    override suspend fun createCompetition(data: NewCompetition): Flow<BaseResponse<Boolean>> {
        return  dataSource.createCompetition(data)
    }

    override suspend fun joinCompetition(uid: String): Flow<BaseResponse<Boolean>> {
        return dataSource.joinCompetition(uid)
    }

    override suspend fun voteVideo(id: String): Flow<BaseResponse<Boolean>> {
        return dataSource.voteVideo(videoId = id)
    }

}
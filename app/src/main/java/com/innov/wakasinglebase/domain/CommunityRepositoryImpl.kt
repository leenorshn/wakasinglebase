package com.innov.wakasinglebase.domain

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.CommunityModel
import com.innov.wakasinglebase.data.repository.CommunityRepository
import com.innov.wakasinglebase.data.source.CommunityDataSource
import com.wakabase.type.NewCommunity
import com.wakabase.type.NewMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val datasource:CommunityDataSource
):CommunityRepository {
    override suspend fun createCommunity(data: NewCommunity): Flow<BaseResponse<Boolean>> {
        return datasource.createCommunity(data)
    }

    override suspend fun publishMessage(data: NewMessage): Flow<BaseResponse<Boolean>> {
        return datasource.publishMessage(data)
    }

    override suspend fun getCommunity(id: String): Flow<BaseResponse<CommunityModel?>> {
        return  datasource.getCommunity(id)
    }

    override suspend fun getCommunities(): Flow<BaseResponse<List<CommunityModel>>> {
        return  datasource.getCommunities()
    }
}
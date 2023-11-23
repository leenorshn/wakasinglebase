package com.innov.wakasinglebase.data.repository

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.CommunityModel
import com.wakabase.type.NewCommunity
import com.wakabase.type.NewMessage
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {

    suspend fun createCommunity(data:NewCommunity):Flow<BaseResponse<Boolean>>
    suspend fun publishMessage(data:NewMessage):Flow<BaseResponse<Boolean>>

    suspend fun getCommunity(id:String):Flow<BaseResponse<CommunityModel?>>
    suspend fun getCommunities():Flow<BaseResponse<List<CommunityModel>>>
}
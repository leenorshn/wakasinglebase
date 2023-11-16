package com.innov.wakasinglebase.data.repository.threads

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.ThreadModel
import com.wakabase.type.NewThread
import kotlinx.coroutines.flow.Flow

interface ThreadsRepository {

    suspend fun getAllThreads():Flow<BaseResponse<List<ThreadModel>>>
    suspend fun getMyThreads():Flow<BaseResponse<List<ThreadModel>>>

    suspend fun getSingleThread(id:String):Flow<BaseResponse<ThreadModel>>

    suspend fun createThread(data:NewThread):Flow<BaseResponse<Boolean>>
    suspend fun attachThreadToVideo(videoId:String,threadId:String):Flow<BaseResponse<Boolean>>
}
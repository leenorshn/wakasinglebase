package com.innov.wakasinglebase.domain.threads

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.ThreadModel
import com.innov.wakasinglebase.data.repository.threads.ThreadsRepository
import com.innov.wakasinglebase.data.source.ThreadDataSource
import com.wakabase.type.NewThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThreadsRepositoryImpl @Inject constructor(
    private val dataSource: ThreadDataSource
):ThreadsRepository {
    override suspend fun getAllThreads(): Flow<BaseResponse<List<ThreadModel>>> {
        return dataSource.fetchThreads()
    }

    override suspend fun getMyThreads(): Flow<BaseResponse<List<ThreadModel>>> {
        return dataSource.fetchMyThreads()
    }

    override suspend fun getSingleThread(id: String): Flow<BaseResponse<ThreadModel>> {
        return dataSource.fetchThread(id)
    }

    override suspend fun createThread(data: NewThread): Flow<BaseResponse<Boolean>> {
        return dataSource.createThread(data)
    }

    override suspend fun attachThreadToVideo(
        videoId: String,
        threadId: String
    ): Flow<BaseResponse<Boolean>> {
        return  dataSource.attacheThread(videoId, threadId)
    }
}
package com.innov.wakasinglebase.domain.threads

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.ProductModel
import com.innov.wakasinglebase.data.repository.threads.ProductRepository
import com.innov.wakasinglebase.data.source.ThreadDataSource
import com.wakabase.type.NewProduct

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThreadsRepositoryImpl @Inject constructor(
    private val dataSource: ThreadDataSource
):ProductRepository {


    override suspend fun getMyThreads(): Flow<BaseResponse<List<ProductModel>>> {
        return dataSource.fetchMyProducts()
    }

    override suspend fun getSingleThread(id: String): Flow<BaseResponse<ProductModel>> {
        return dataSource.fetchProduct(id)
    }

    override suspend fun createThread(data: NewProduct): Flow<BaseResponse<Boolean>> {
        return dataSource.createProduct(data)
    }

    override suspend fun attachThreadToVideo(
        videoId: String,
        threadId: String
    ): Flow<BaseResponse<Boolean>> {
        return  dataSource.attacheProduct(videoId, threadId)
    }
}
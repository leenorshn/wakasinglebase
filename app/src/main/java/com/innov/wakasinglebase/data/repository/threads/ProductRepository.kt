package com.innov.wakasinglebase.data.repository.threads

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.ProductModel
import com.wakabase.type.NewProduct
import kotlinx.coroutines.flow.Flow

interface ProductRepository {


    suspend fun getMyThreads():Flow<BaseResponse<List<ProductModel>>>

    suspend fun getSingleThread(id:String):Flow<BaseResponse<ProductModel>>

    suspend fun createThread(data:NewProduct):Flow<BaseResponse<Boolean>>
    suspend fun attachThreadToVideo(videoId:String,threadId:String):Flow<BaseResponse<Boolean>>
}
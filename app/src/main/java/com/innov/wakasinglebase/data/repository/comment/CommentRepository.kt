package com.innov.wakasinglebase.data.repository.comment

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.CommentModel
import kotlinx.coroutines.flow.Flow

//import com.innov.data.source.CommentDataSource.fetchComment

/**
 * Created by innov  on 3/22/2023.
 */
interface CommentRepository{
    suspend fun addComment(videoId:String,comment:String):Flow<BaseResponse<Boolean>>
    fun getComments(videoId: String):Flow<BaseResponse<List<CommentModel>>>
}
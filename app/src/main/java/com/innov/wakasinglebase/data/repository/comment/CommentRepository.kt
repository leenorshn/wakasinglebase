package com.innov.wakasinglebase.data.repository.comment

import com.innov.wakasinglebase.data.model.CommentList
//import com.innov.data.source.CommentDataSource.fetchComment
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by innov  on 3/22/2023.
 */
class CommentRepository @Inject constructor() {
    fun getComment(videoId: String): Flow<CommentList>? {
        //return fetchComment(videoId)
        return  null
    }
}
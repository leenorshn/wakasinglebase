package com.innov.wakasinglebase.domain.comment

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.CommentModel
import com.innov.wakasinglebase.data.source.CommentDataSource
import com.wakabase.type.NewComment
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCommentOnVideoUseCase @Inject constructor(private val dataSource: CommentDataSource){
    operator fun invoke(videoId: String): Flow<BaseResponse<List<CommentModel>>> {
//        return commentRepository.getComment(videoId)
        return dataSource.getComments(videoId)
    }
}



class CreateCommentOnVideoUseCase @Inject constructor(private val dataSource: CommentDataSource){
    operator fun invoke(comment: String,videoId: String): Flow<BaseResponse<Boolean>> {
//        return commentRepository.getComment(videoId)
        return dataSource.addComment(NewComment(comment=comment,videoId=videoId))
    }
}
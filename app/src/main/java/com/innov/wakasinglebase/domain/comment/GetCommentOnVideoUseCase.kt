package com.innov.wakasinglebase.domain.comment

import com.innov.wakasinglebase.data.model.CommentList
import com.innov.wakasinglebase.data.repository.comment.CommentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCommentOnVideoUseCase @Inject constructor(private val commentRepository: CommentRepository) {
    operator fun invoke(videoId: String): Flow<CommentList>? {
//        return commentRepository.getComment(videoId)
        return null
    }
}
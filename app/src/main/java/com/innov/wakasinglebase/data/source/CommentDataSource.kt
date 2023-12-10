package com.innov.wakasinglebase.data.source


import com.apollographql.apollo3.ApolloClient
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.CommentModel
import com.innov.wakasinglebase.data.model.toCommentModel
import com.wakabase.CreateCommentMutation
import com.wakabase.GetCommentsQuery
import com.wakabase.type.NewComment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by innov Victor on 4/3/2023.
 */
class CommentDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {

    fun addComment(comment: NewComment): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            var res=apolloClient.mutation(CreateCommentMutation(comment,)).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error of joining a competion"))
            }
            else{
                emit(BaseResponse.Success(true))
            }
        }
    }

    fun getComments(id: String): Flow<BaseResponse<List<CommentModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            var res=apolloClient.query(GetCommentsQuery(id)).execute()
            if (res.hasErrors()){
                emit(BaseResponse.Error("Error of joining a competion"))
            }
            else{
                val comments=res.data?.comments?.map {
                    it.toCommentModel()
                }?: emptyList()
                emit(BaseResponse.Success(comments))
            }
        }
    }


}
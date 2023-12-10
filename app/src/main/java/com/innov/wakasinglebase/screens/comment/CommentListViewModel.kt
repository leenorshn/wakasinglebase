package com.innov.wakasinglebase.screens.comment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import com.innov.wakasinglebase.domain.comment.CreateCommentOnVideoUseCase
import com.innov.wakasinglebase.domain.comment.GetCommentOnVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentListViewModel @Inject constructor(
    private val getCommentOnVideoUseCase: GetCommentOnVideoUseCase,
    private val createCommentOnVideoUseCase: CreateCommentOnVideoUseCase,
    private val repository:AuthRepositoryImpl,
) : BaseViewModel<ViewState, CommentEvent>() {

    init {
        getSignedInUser()
    }

    var user by mutableStateOf(UserModel())

    private fun getContentCreator(video:String) {
       // user=video.authorDetails!!
        viewModelScope.launch {
            getCommentOnVideoUseCase(videoId=video).collect {
                when(it){
                    is BaseResponse.Error -> {
                        updateState(ViewState(error = "Error when charging comment"))
                    }
                    BaseResponse.Loading -> {
                        updateState(ViewState(isLoading = true ))
                    }
                    is BaseResponse.Success -> {
                        updateState(ViewState(comments = it.data))
                    }
                }
            }
        }
    }

    override fun onTriggerEvent(event: CommentEvent) {

        when(event){
            is CommentEvent.OnLoadCommentsEvent -> {
                getContentCreator(event.video!!)
            }
            is CommentEvent.OnPublishEvent -> {

                onPublishComment(comment=event.comment, videoId = event.videoId)
            }
        }
    }

    private fun onPublishComment(comment:String,videoId:String){
        viewModelScope.launch {
            createCommentOnVideoUseCase.invoke(comment=comment,videoId=videoId).collect{

            }
        }
    }

    private fun getSignedInUser() {
        viewModelScope.launch {
            repository.me().collect {result->
                when(result){
                    is BaseResponse.Loading->{

                    }
                    is BaseResponse.Success->{
                       user=result.data!!
                    }
                    is BaseResponse.Error->{

                    }
                }


            }
        }
    }


}
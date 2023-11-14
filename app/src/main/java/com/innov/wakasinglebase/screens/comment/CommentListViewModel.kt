package com.innov.wakasinglebase.screens.comment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.domain.comment.GetCommentOnVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentListViewModel @Inject constructor(
    private val getCommentOnVideoUseCase: GetCommentOnVideoUseCase
) : BaseViewModel<ViewState, CommentEvent>() {

    var user by mutableStateOf(UserModel())

    private fun getContentCreator(video:VideoModel) {
        user=video.authorDetails!!
        viewModelScope.launch {
            getCommentOnVideoUseCase(videoId=video.videoId)?.collect {
                updateState(ViewState(comments = it))
            }
        }
    }

    override fun onTriggerEvent(event: CommentEvent) {

        when(event){
            is CommentEvent.OnLoadCommentsEvent -> {
                getContentCreator(event.video)
            }
            is CommentEvent.OnPublishEvent -> {

            }
        }
    }


}
package com.innov.wakasinglebase.screens.camera.publication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.domain.repository.VideoRepositoryImpl
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import com.innov.wakasinglebase.signin.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PublicationViewModel @Inject constructor(
    private val videoRepositoryImpl: VideoRepositoryImpl,
    private val  repository: AuthRepositoryImpl,
) : ViewModel() {


    var uiState = mutableStateOf(UiState())
    init {
        getUser()
        //updateState(ViewState(currentUser = uiState.value.currentUser))

    }

    private fun getUser() {
        viewModelScope.launch {
            repository.me().collect {result->

                //updateState((viewState.value ?: ViewState()).copy(currentUser = it.data))
                when(result){
                    is BaseResponse.Loading->{
                        uiState.value = uiState.value.copy(
                            isLoading = true
                        )
                    }
                   is BaseResponse.Success ->{
                        uiState.value = uiState.value.copy(
                            isLoading = false, currentUser = result.data
                        )
                    }
                    is BaseResponse.Error ->{
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            currentUser = null,
                            signinError = result.error
                        )
                    }
                }
            }
        }
    }


      fun publishVideoData( fileName:String, category:String, title:String, description:String){
        viewModelScope.launch {
            uiState.value.currentUser?.let { videoRepositoryImpl.createVideo(videoId = fileName,category=category,title=title, author = it,description=description) }
        }
    }
}
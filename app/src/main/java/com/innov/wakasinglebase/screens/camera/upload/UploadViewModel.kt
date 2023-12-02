package com.innov.wakasinglebase.screens.camera.upload

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.data.repository.authentification.AuthRepository
import com.innov.wakasinglebase.screens.camera.CameraMediaEvent
import com.innov.wakasinglebase.screens.camera.ViewState
import com.innov.wakasinglebase.signin.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val  repository: AuthRepository,


    ) : BaseViewModel<ViewState, CameraMediaEvent>() {

    val uiState = mutableStateOf(UiState())
    init {
        getUser()
        updateState(ViewState(currentUser = uiState.value.currentUser))

    }

    override fun onTriggerEvent(event: CameraMediaEvent) {
        when (event) {
            CameraMediaEvent.EventFetchCurrentUser -> getUser()
            else -> {}
        }
    }



    private fun getUser() {
        viewModelScope.launch {
            repository.me().collect {result->

                //updateState((viewState.value ?: ViewState()).copy(currentUser = it.data))
                when(result){
                     BaseResponse.Loading->{
                        uiState.value = uiState.value.copy(
                            isLoading = true
                        )
                    }
                    is BaseResponse.Success->{
                        uiState.value = uiState.value.copy(
                            isLoading = false, currentUser = result.data
                        )
                    }
                    is BaseResponse.Error->{
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            currentUser = null,
                            signinError = result.error
                        )
                    }

                    else -> {}
                }
            }
        }
    }


}
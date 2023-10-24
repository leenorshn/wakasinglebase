package com.innov.wakasinglebase.screens.myprofil

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.data.repository.authentification.AuthRepository
import com.innov.wakasinglebase.signin.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by innov Victor on 4/1/2023.
 */
@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: AuthRepository
) : BaseViewModel<ViewState, SettingEvent>() {
    val uiState = mutableStateOf(UiState())

    init {
        updateState(ViewState(settingUiData = settingUiModel))
        getSignedInUser()
    }

    override fun onTriggerEvent(event: SettingEvent) {
    }

    private fun getSignedInUser() {
        viewModelScope.launch {
            repository.me().collect {result->
                when(result){
                    is BaseResponse.Loading->{
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
                }


            }
        }
    }




}
package com.innov.wakasinglebase.screens.myprofil

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.data.repository.authentification.AuthRepository
import com.innov.wakasinglebase.data.repository.authentification.TokenRepository
import com.innov.wakasinglebase.signin.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by innov Victor on 4/1/2023.
 */
@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val tokenRepo:TokenRepository,
    @ApplicationContext context: Context
) : BaseViewModel<ViewState, SettingEvent>() {
    val uiState = mutableStateOf(UiState())

    init {
        tokenRepo.init(context)
        updateState(ViewState(settingUiData = settingUiModel))
        getSignedInUser()
    }

    override fun onTriggerEvent(event: SettingEvent) {
        when(event){
            SettingEvent.OnLogout -> {
                tokenRepo.removeToken()
            }
        }
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
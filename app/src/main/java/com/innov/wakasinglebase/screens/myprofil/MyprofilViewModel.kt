package com.innov.wakasinglebase.screens.myprofil

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.SignInClient
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.data.repository.authentification.AuthRepository
import com.innov.wakasinglebase.signin.Result
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
            repository.getSignedInUser().collect {result->
                when(result){
                    is Result.Loading->{
                        uiState.value = uiState.value.copy(
                            isLoading = true
                        )
                    }
                    is Result.Success->{
                        uiState.value = uiState.value.copy(
                            isLoading = false, currentUser = result.data
                        )
                    }
                    is Result.Error->{
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            currentUser = null,
                            signinError = result.e?.message
                        )
                    }
                }


            }
        }
    }

    fun signOut(oneTapClient : SignInClient){
        viewModelScope.launch {
            repository.signOut(oneTapClient).collect{result->
                when(result){
                    is Result.Loading->{
                        uiState.value = uiState.value.copy(
                            isLoading = true
                        )
                    }
                    is Result.Success->{
                        uiState.value = uiState.value.copy(
                            isLoading = false, currentUser = null
                        )
                    }
                    is Result.Error->{
                        uiState.value = uiState.value.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }


}
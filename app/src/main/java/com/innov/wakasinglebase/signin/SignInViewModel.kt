package com.innov.wakasinglebase.signin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.repository.authentification.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel(){
    val uiState = mutableStateOf(UiState())

    init {
        getSignedInUser()
    }

    fun onSignInResult(result : SignInResult){
        uiState.value = uiState.value.copy(
            isSignInSuccessfull = result.data != null,
            signinError = result.errorMessage,
            currentUser = result.data
        )
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
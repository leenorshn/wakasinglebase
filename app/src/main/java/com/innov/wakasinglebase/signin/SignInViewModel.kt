package com.innov.wakasinglebase.signin

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.SignInClient
import com.innov.wakasinglebase.data.repository.authentification.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
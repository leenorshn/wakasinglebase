package com.innov.wakasinglebase.signin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel:ViewModel() {
    private val _state= MutableStateFlow(SignInState())
    val state=_state.asStateFlow()

    fun onSignInResult(result: SignInResult){
        _state.update {it.copy(
            isSignSuccessFul = result.data!=null,
            signInError=result.errorMessage
        )

        }
    }
    fun resetState() {
        _state.update {
            it.copy(
                isSignSuccessFul = false,
                signInError = null
            )


        }
    }
}
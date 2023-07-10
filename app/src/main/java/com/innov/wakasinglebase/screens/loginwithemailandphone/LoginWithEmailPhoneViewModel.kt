package com.innov.wakasinglebase.screens.loginwithemailandphone

import com.innov.wakasinglebase.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Created by innov Victor on 3/28/2023.
 */

@HiltViewModel
class LoginWithEmailPhoneViewModel @Inject constructor(
) : BaseViewModel<ViewState, LoginEmailPhoneEvent>() {
    private val _settledPage = MutableStateFlow<Int?>(null)
    val settledPage = _settledPage.asStateFlow()

    private val _phoneNumber =
        MutableStateFlow<Pair<String, String?>>(Pair("", null))  //Pair(value,errorMsg)
    val phoneNumber = _phoneNumber.asStateFlow()

    private val _dialCode = MutableStateFlow<Pair<String, String?>>(Pair("RDC +243", null))
    val dialCode = _dialCode.asStateFlow()





    override fun onTriggerEvent(event: LoginEmailPhoneEvent) {
        when (event) {
            is LoginEmailPhoneEvent.EventPageChange -> _settledPage.value = event.settledPage

            is LoginEmailPhoneEvent.OnChangePhoneNumber -> _phoneNumber.value =
                _phoneNumber.value.copy(first = event.newValue)
        }
    }


}
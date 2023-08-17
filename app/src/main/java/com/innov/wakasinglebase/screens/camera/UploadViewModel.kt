package com.innov.wakasinglebase.screens.camera

import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.cameramedia.GetAuthorUserCase
import com.innov.wakasinglebase.domain.cameramedia.GetTemplateUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject


class UploadViewModel @Inject constructor(
    private val getAuthorUserCase: GetAuthorUserCase,

    ) : BaseViewModel<ViewState, CameraMediaEvent>() {


    init {
        getUser()
    }

    override fun onTriggerEvent(event: CameraMediaEvent) {
        when (event) {
            CameraMediaEvent.EventFetchCurrentUser -> getUser()
            else -> {}
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            getAuthorUserCase().collect {
                updateState((viewState.value ?: ViewState()).copy(currentUser = it.data))
            }
        }
    }
}
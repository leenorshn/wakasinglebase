package com.innov.wakasinglebase.screens.camera
//package com.innov.cameramedia

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.cameramedia.GetTemplateUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by innov Victor on 3/15/2023.
 */
@HiltViewModel
class CameraMediaViewModel @Inject constructor(
    private val getTemplateUseCase: GetTemplateUseCase,

) : BaseViewModel<ViewState, CameraMediaEvent>() {

    init {
        getTemplates()
    }

    override fun onTriggerEvent(event: CameraMediaEvent) {
        when (event) {
            CameraMediaEvent.EventFetchTemplate -> getTemplates()
            else ->{

            }
        }
    }

    private fun getTemplates() {
        viewModelScope.launch {
            getTemplateUseCase().collect {
                updateState((viewState.value ?: ViewState()).copy(templates = it))
            }
        }
    }



}
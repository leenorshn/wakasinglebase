package com.innov.wakasinglebase.screens.camera
//package com.innov.cameramedia

import com.innov.wakasinglebase.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by innov Victor on 3/15/2023.
 */
@HiltViewModel
class CameraMediaViewModel @Inject constructor(


) : BaseViewModel<ViewState, CameraMediaEvent>() {

    init {
        //getTemplates()
    }

    override fun onTriggerEvent(event: CameraMediaEvent) {
        when (event) {

            else ->{

            }
        }
    }





}
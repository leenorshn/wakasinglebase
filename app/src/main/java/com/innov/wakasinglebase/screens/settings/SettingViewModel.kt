package com.innov.wakasinglebase.screens.settings

import com.innov.wakasinglebase.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by innov Victor on 4/1/2023.
 */
@HiltViewModel
class SettingViewModel @Inject constructor(
) : BaseViewModel<ViewState, SettingEvent>() {

    init {
        updateState(ViewState(settingUiData = settingUiModel))
    }

    override fun onTriggerEvent(event: SettingEvent) {
    }


}
package com.innov.wakasinglebase.screens.gift


import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.domain.cameramedia.GetTemplateUseCase
import com.innov.wakasinglebase.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiftViewModel @Inject constructor(
    private val getTemplateUseCase: GetTemplateUseCase
) : BaseViewModel<ViewState, CommunityMediaEvent>() {

    init {
        getTemplates()
    }

    override fun onTriggerEvent(event: CommunityMediaEvent) {
        when (event) {
            CommunityMediaEvent.EventFetchTemplate -> getTemplates()
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
package com.innov.wakasinglebase.screens.gift

import com.innov.wakasinglebase.data.model.TemplateModel

data class ViewState(
    val templates: List<TemplateModel>? = null
)

sealed class CommunityMediaEvent {
    object EventFetchTemplate : CommunityMediaEvent()

}
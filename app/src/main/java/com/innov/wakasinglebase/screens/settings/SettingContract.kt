package com.innov.wakasinglebase.screens.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.innov.wakasinglebase.R

/**
 * Created by innov Victor on 4/1/2023.
 */

data class ViewState(
    val settingUiData: Map<String, List<RowItem>>? = null
)

sealed class SettingEvent {

}


data class RowItem(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val rightSideClickItem: @Composable (() -> Unit)? = null,
)

val settingUiModel: Map<String, List<RowItem>> = mapOf(
    "Account" to listOf(
        RowItem(icon = R.drawable.ic_profile_fill, title = R.string.my_account),
        RowItem(icon = R.drawable.ic_phone, title = R.string.playback),
    ),
    "Payments" to listOf(
        RowItem(icon = R.drawable.ic_dollar, title = R.string.live),
        RowItem(icon = R.drawable.ic_graph, title = R.string.playback),
    ),
    "Secutity" to listOf(
        RowItem(icon = R.drawable.ic_recycler_bin, title = R.string.free_up_space),
        RowItem(icon = R.drawable.ic_cle, title = R.string.data_saver),
        RowItem(icon = R.drawable.ic_wallpaper, title = R.string.wallpaper)
    ),
    "Support & About" to listOf(
        RowItem(icon = R.drawable.ic_flag, title = R.string.report_a_problem),
        RowItem(icon = R.drawable.ic_support, title = R.string.support),
        RowItem(icon = R.drawable.ic_info, title = R.string.terms_and_policies)
    )
)
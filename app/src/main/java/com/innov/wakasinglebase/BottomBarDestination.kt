package com.innov.wakasinglebase


import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.innov.wakasinglebase.core.DestinationRoute.CAMERA_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.EXPLORE_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.GIFT_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.HOME_SCREEN_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.MY_PROFILE_ROUTE

/**
 * Created by innov Victor on 3/14/2023.
 */

enum class BottomBarDestination(
    val route: String,
    @StringRes val title: Int? = null,
    @DrawableRes val unFilledIcon: Int,
    @DrawableRes val filledIcon: Int? = null,
    @DrawableRes val darkModeIcon: Int? = null
) {



    HOME(
        route = HOME_SCREEN_ROUTE,
        title = R.string.home,
        unFilledIcon = R.drawable.ic_home,
        filledIcon = R.drawable.ic_home_fill
    ),

    EXPLORE(
        route = EXPLORE_ROUTE,
        title = R.string.explore,
        unFilledIcon = R.drawable.panier_24_out,
        filledIcon = R.drawable.panier_24_fill
    ),

    ADD(
        route = CAMERA_ROUTE,
        unFilledIcon = R.drawable.logo_tiktok_compose,
        darkModeIcon = R.drawable.logo_tiktok_compose
    ),

    GIFT(
        route = GIFT_ROUTE,
        title = R.string.gift,
        unFilledIcon = R.drawable.ic_inbox,
        filledIcon = R.drawable.ic_inbox_fill
    ),

    Profile(
        route = MY_PROFILE_ROUTE,
        title = R.string.profile,
        unFilledIcon = R.drawable.ic_profile,
        filledIcon = R.drawable.ic_profile_fill
    ),

}
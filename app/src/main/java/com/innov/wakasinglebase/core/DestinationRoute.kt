package com.innov.wakasinglebase.core

import com.innov.wakasinglebase.core.DestinationRoute.PassedKey.USER_ID
import com.innov.wakasinglebase.core.DestinationRoute.PassedKey.VIDEO_INDEX

/**
 * Created by Victor on 3/19/2023.
 */
object DestinationRoute {
    const val MONETISATION_ROUTE="MONETISATION_ROUTE"
    const val RECHARGE_ROUTE="RECHARGE_ROUTE"
    const val NOTIFICATION_ROUTE="NOTIFICATION_ROUTE"
    const val WATCH_COMPETITION_ROUTE="WATCH_COMPETITION_ROUTE/id={id}"
    const val JOIN_COMPETITION_ROUTE="JOIN_COMPETITION_ROUTE/id={id}"

    const val MY_VIDEO_ROUTE= "MY_VIDEO_ROUTE/id={videoId}"
    const val MY_BUSINESS_ROUTE= "MY_BUSINESS_ROUTE/"
    const val EDIT_PROFILE="EDIT_PROFILE_ROUTE"
    const val PROFILE_SCREEN_AVATAR="PROFILE_SCREEN_AVATAR"
    const val FOLLOWER_ROUTE="follower_route"
    const val LOADING_SCREEN= "loading_screen"
    const val MAIN_NAV_ROUTE="main_route"
    const val AUTH_ROUTE="auth_route"

    const val PUBLICATION_SCREEN_ROUTE="publication_screen_route/uri={uri}"
    const val HOME_SCREEN_ROUTE = "home_screen_route"
    const val COMMENT_BOTTOM_SHEET_ROUTE = "comment_bottom_sheet_route"
    const val CREATOR_PROFILE_ROUTE = "creator_profile_route"

    const val CREATOR_VIDEO_ROUTE = "creator_video_route"
    const val FORMATTED_COMPLETE_CREATOR_VIDEO_ROUTE =
        "$CREATOR_VIDEO_ROUTE/{$USER_ID}/{$VIDEO_INDEX}"

    const val LOTTO_ROUTE = "Lotto_route"
    const val CHAT_ROUTE = "CHAT_ROUTE/conversation={conversation}"
    const val MY_PROFILE_ROUTE = "my_profile_route"
    const val THREADS_ROUTE = "THREADS_ROUTE"
    const val CAMERA_ROUTE = "camera_route"

    const val AUTHENTICATION_ROUTE = "authentication_route"
    const val LOGIN_OR_SIGNUP_WITH_PHONE_ROUTE = "login_signup_route"
    const val OPT_SCREEN_ROUTE="opt_screen_route/phone={phone}"

    const val SETTING_ROUTE="setting_route"
    const val UPLOAD_ROUTE="upload_route/uri={uri}"


    object PassedKey {
        const val USER_ID = "user_id"
        const val VIDEO_INDEX = "video_index"
    }
}


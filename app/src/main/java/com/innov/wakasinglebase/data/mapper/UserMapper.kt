package com.innov.wakasinglebase.data.mapper

import com.innov.wakasinglebase.data.model.AuthModel
import com.innov.wakasinglebase.data.model.UserModel
import com.wakabase.FollowersQuery
import com.wakabase.LoginOrCreateAccountMutation
import com.wakabase.MeQuery
import com.wakabase.UsersQuery


fun MeQuery.Me.toUserModel():UserModel{
    return UserModel(
        uid = id,
        name =name,
        uniqueUserName=name.replace(" ","_"),
        phone = phone,
        profilePic = profilePic,
        balance = balance,
        bio=bio,
        isVerified = isVerified?:false,
        hasContract = hasContract?:false
    )
}

fun LoginOrCreateAccountMutation.LoginOrCreateAccount.toAuthModel():AuthModel{
    return AuthModel(token = "$token")
}

fun FollowersQuery.Friend.toUserModel():UserModel{
    return UserModel(
        uid = id,
        name=name,
        phone = phone,
        bio = bio,
        profilePic = profilePic
    )

}

fun UsersQuery.User.toUserModel():UserModel{
    return UserModel(
        uid = id,
        name=name,
        phone = phone,
        bio = bio,
        profilePic = profilePic
    )

}
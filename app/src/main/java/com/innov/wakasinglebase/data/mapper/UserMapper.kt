package com.innov.wakasinglebase.data.mapper

import com.innov.wakasinglebase.data.model.AuthModel
import com.innov.wakasinglebase.data.model.UserModel
import com.wakabase.FriendsQuery
import com.wakabase.LoginOrCreateAccountMutation
import com.wakabase.MeQuery
import com.wakabase.MyFriendsQuery
import com.wakabase.UserQuery
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
        isMonetizated = isMonetizated,
        isVerified = isVerified?:false,
        hasContract = hasContract?:false,
        followers = followers,
        following = following
    )
}



fun UserQuery.User.toUserModel():UserModel{
    return UserModel(
        uid = id,
        name =name,
        uniqueUserName=name.replace(" ","_"),
        phone = phone,
        profilePic = profilePic,
        balance = balance,
        bio=bio,
        isMonetizated = isMonetizated,
        isVerified = isVerified?:false,
        hasContract = hasContract?:false,
        followers = followers,
        following = following
    )
}

fun LoginOrCreateAccountMutation.LoginOrCreateAccount.toAuthModel():AuthModel{
    return AuthModel(token = "$token")
}



fun UsersQuery.User.toUserModel():UserModel{
    return UserModel(
        uid = id,
        name =name,
        uniqueUserName=name.replace(" ","_"),
        phone = phone,
        profilePic = profilePic,
        balance = balance,
        bio=bio,
        isMonetizated = isMonetizated,
        isVerified = isVerified?:false,
        hasContract = hasContract?:false,
        followers = followers,
        following = following
    )

}

fun FriendsQuery.Friend.toUserModel():UserModel{
    return UserModel(
        uid = id,
        name =name,
        uniqueUserName=name.replace(" ","_"),
        phone = phone,
        profilePic = profilePic,
        balance = balance,
        bio=bio,
        isMonetizated = isMonetizated,
        isVerified = isVerified?:false,
        hasContract = hasContract?:false,
        followers = followers,
        following = following
    )
}

fun MyFriendsQuery.MyFriend.toUserModel():UserModel{
    return UserModel(
        uid = id,
        name =name,
        uniqueUserName=name.replace(" ","_"),
        phone = phone,
        profilePic = profilePic,
        balance = balance,
        bio=bio,
        isMonetizated = isMonetizated,
        isVerified = isVerified?:false,
        hasContract = hasContract?:false,
        followers = followers,
        following = following
    )
}
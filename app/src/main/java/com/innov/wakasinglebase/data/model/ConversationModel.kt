package com.innov.wakasinglebase.data.model

import com.wakabase.ChatsQuery
import java.text.SimpleDateFormat
import java.util.Date

data class ConversationModel(
    val id:String,
    val sender:UserModel,
    val receiver:UserModel,
    val chats:List<ChatModel>,
)

data class ChatModel(
    val id: String,
    val sender:String,
    val message:String,
    val createdAt:String
)

fun ChatsQuery.Chat.toChatModel():ChatModel{
    return ChatModel(
        id=id,
        sender=sender,
        message=message,
        createdAt=convertTime(createdAt.toLong())
    )
}

fun convertTime(time: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yy hh:mm a")
    val netDate = Date(time)
    return sdf.format(netDate)
}

fun ChatsQuery.Conversation.toConversationModel():ConversationModel{
    return ConversationModel(
        id=id,
        sender= UserModel(
            uid=sender.id,
            name = sender.name,
            profilePic = sender.profilePic,

        ),
        receiver=UserModel(
            uid=receiver.id,
            name = receiver.name,
            profilePic = receiver.profilePic,
            ),
        chats=chats.map { it.toChatModel() }
    )
}

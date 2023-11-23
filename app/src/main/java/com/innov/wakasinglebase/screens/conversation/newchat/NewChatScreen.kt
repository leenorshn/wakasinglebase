package com.innov.wakasinglebase.screens.conversation.newchat

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewChatScreen(
    navController: NavController,
    viewModel: NewChatViewModel= hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val message = "Say Hi to "
    Scaffold (
        topBar = {
            TopAppBar(title = {
                Text(text = "Choose a user")
            },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp()}) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription ="" )
                    }
                })
        }
    ){
        LazyColumn(modifier = Modifier.padding(it)){
            if (viewState?.users != null) {
               viewState?.users?.let { users ->
                   items(users) { user ->
                       val avatar =
                           if (user.profilePic.isNullOrEmpty()) "https://d2y4y6koqmb0v7.cloudfront.net/profil.png"
                           else user.profilePic
                       val name = if (user.name.isNullOrEmpty()) "@User${
                           user.uid
                       }" else user.name
                       ListItem(
                           leadingContent = {
                               AsyncImage(
                                   model = avatar,
                                   contentDescription = "",
                                   contentScale = ContentScale.Crop,
                                   modifier = Modifier
                                       .size(56.dp)
                                       .clip(RoundedCornerShape(50))
                               )
                           },
                           headlineContent = {

                               Text(
                                   text = "$name",
                                   fontSize = 20.sp,
                                   fontWeight = FontWeight.Medium,
                                   overflow = TextOverflow.Ellipsis,
                                   maxLines = 1,
                               )
                           }, supportingContent = {
                               Text(
                                   text = "$message to $name \uD83E\uDD70",
                                   fontSize = 12.sp,
                                   color = Color.Gray
                               )
                           },

                           trailingContent = {

                               OutlinedButton(onClick = {
                                   viewModel.onTriggerEvent(NewChatEvent.OnStartedConversation("${user.uid}"))
                               }) {
                                   Text(text = "Send a \u270B", fontSize = 12.sp)
                               }

                           })
                       Divider()

                   }
               }


           }
        }
    }
}
package com.innov.wakasinglebase.screens.conversation.chats

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Chat") })
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)){
            items(3){
                Text(text = "$it")
            }
        }
    }
}
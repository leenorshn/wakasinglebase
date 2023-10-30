package com.innov.wakasinglebase.signin.follower

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun FollowerScreen(
    navController: NavController,
    viewModel: FollowerViewModel= hiltViewModel()
) {
    Scaffold {
        LazyColumn(modifier = Modifier.padding(it)){
            item { 
                Text(text = "Connaissez-vous ces personnes")
            }
        }
    }
}
package com.innov.wakasinglebase.screens.community.newCommunity

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.common.CustomButton
import com.innov.wakasinglebase.common.CustomIconButton
import com.innov.wakasinglebase.common.CustomToggleButton
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.ui.theme.PrimaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCommunityScreen(
    navController: NavController,
    viewModel: NewCommunityViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val viewState by viewModel.viewState.collectAsState()
    val context = LocalContext.current
    var members= remember {
        mutableListOf<String>()
    }

    var dialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(NewCommunityEvent.OnLoadFriendsEvent)
    }

    LaunchedEffect(key1 = uiState.success) {
        if (uiState.success) {
            navController.navigateUp()
            Toast.makeText(context, "Community created ", Toast.LENGTH_LONG).show()
        }
        if (uiState.error != null) {
            Toast.makeText(context, "Error of creating new community ", Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.onTriggerEvent(NewCommunityEvent.OnMembersEnteredEvent(viewModel.members))
    }
    val maxCharacters=128
    val maxCharName=50
    Scaffold(
        topBar = {
            TopAppBar(title = { },
                navigationIcon = {
                    TextButton(onClick = { navController.navigateUp() }) {
                        Text(text = "Back", color = Color.Black)
                    }
                }, actions = {
                    TextButton(
                        enabled = viewModel.isValid(),
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = PrimaryColor,
                        ),
                        onClick = { viewModel.onTriggerEvent(NewCommunityEvent.OnSubmitEvent) }) {
                        val t = if (uiState.isLoading) "loading ..." else "Create"
                        Text(text = t)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp)
        ) {
            16.dp.Space()
            Text(
                "Create a community ton influence your network!",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            16.dp.Space()
            Text(text = "Name", fontSize = 12.sp, color = Color.Gray)
            4.dp.Space()
            OutlinedTextField(
                value = viewModel.name, onValueChange = {
                    if(it.length<=maxCharName){
                        viewModel.name = it
                        viewModel.onTriggerEvent(NewCommunityEvent.OnNameEnteredEvent(it))
                    }
                },
                placeholder = {
                    Text(text = "Community name")
                },
                singleLine = true,
                maxLines = 1,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                    focusedContainerColor = Color.LightGray

                ),
                supportingText = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(text = "${viewModel.name.length}/$maxCharName", color = Color.Black)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    //.height(56.dp)
                    .clip(RoundedCornerShape(10))
            )
            16.dp.Space()
            Text(text = "Philosophy", fontSize = 12.sp, color = Color.Gray)
            4.dp.Space()
            OutlinedTextField(
                value = viewModel.philosophy, onValueChange = {
                    if (it.length <= maxCharacters) {
                        viewModel.philosophy = it
                        viewModel.onTriggerEvent(NewCommunityEvent.OnPhilosophyEnteredEvent(it))
                    }
                },
                placeholder = {
                    Text(text = "What is yours philosophy you community believe In.")
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                    focusedContainerColor = Color.LightGray

                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                maxLines = 5,
                //isError = true,
                supportingText = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(text = "${viewModel.philosophy.length}/$maxCharacters", color = Color.Black)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(16))
            )
            20.dp.Space()
            var selectedValue by remember { mutableStateOf("0.75") }
            Text(
                text = "How much you'll pay per member per month? ",
                fontSize = 12.sp,
                color = Color.Gray
            )
            8.dp.Space()
            CustomToggleButton(
                values = listOf("0.75", "1.75", "3.3"),
                initialValue = selectedValue,
                onValueChanged = { newValue ->
                    selectedValue = newValue
                    viewModel.onTriggerEvent(
                        NewCommunityEvent.OnSubscriptionSelectedEvent(
                            selectedValue.toDouble()
                        )
                    )
                }
            )
            20.dp.Space()
            Text(text = "You'll not be there your self ", fontSize = 12.sp, color = Color.Gray)
            8.dp.Space()
            CustomIconButton(buttonText = "Add members", icon = R.drawable.ic_friends) {
                dialog = true

            }
            if (viewModel.members.isNotEmpty()){
                Text(text = "${viewModel.members.size} members added")
            }

        }
    }
    if (dialog) {
        Dialog(
            onDismissRequest = { dialog = false },

            ) {
            if (viewState?.isLoading == true) {
                Box(
                    modifier = Modifier.fillMaxHeight(0.5f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryColor)
                }
            }
            LazyVerticalGrid(
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(20))
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(8)),
                columns = GridCells.Fixed(2),
            ) {
                viewState?.friends?.let {
                    items(it) { user ->
                        Card {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,


                                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
                            ) {
                                val img =
                                    if (user.profilePic.isNullOrEmpty()) "https://d2y4y6koqmb0v7.cloudfront.net/profil.png" else user.profilePic
                                AsyncImage(
                                    model = img, contentDescription = "",
                                    contentScale = ContentScale.Crop,

                                    modifier = Modifier
                                        .size(72.dp)
                                        .clip(
                                            RoundedCornerShape(50)
                                        ),
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "@${user.name}",
                                    color = Color.Black,
                                    fontSize = 13.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                )
                            }
                            CustomButton(buttonText = "Add") {
                                viewModel.members.add("${user.uid}")
                                dialog=false
                            }
                        }
                    }
                }

            }
        }
    }
}








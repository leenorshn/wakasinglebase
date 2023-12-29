package com.innov.wakasinglebase.screens.createprofile.creatorvideo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.common.ContentSearchBar
import com.innov.wakasinglebase.common.WakawakaVerticalVideoPager
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.ui.theme.SubTextColor

/**
 * Created by innov Victor on 3/22/2023.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatorVideoPagerScreen(
    onClickNavIcon: () -> Unit,
    onclickComment: (videoId: String) -> Unit,
    onClickAudio: (VideoModel) -> Unit,
    onClickUser: (userId: String) -> Unit,
    viewModel: CreatorVideoPagerViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            ContentSearchBar(
                onClickNav = onClickNavIcon,
                onClickSearch = {},
                placeholder = stringResource(id = R.string.find_related_content)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = it.calculateBottomPadding())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            viewState?.creatorVideosList?.let {
                WakawakaVerticalVideoPager(
                    videos = it,
                    showUploadDate = true,
                    onclickComment = onclickComment,
                    onClickLike = {s:String,b:Boolean->
                        viewModel.onTriggerEvent(CreatorVideoEvent.OnLikeVideoEvent(s))
                    },
                   // onclickFavourite = {},
                    onClickAudio = onClickAudio,
                    onClickUser = onClickUser,
                    //onClickVote={},
                    initialPage = viewModel.videoIndex,
                    modifier = Modifier.weight(1f)
                )

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 0.5.dp,
                    color = Color.White.copy(0.2f)
                )
                OutlinedTextField(value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(text = stringResource(R.string.add_comment), color = SubTextColor)
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.Black,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(46.dp),
                    trailingIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(18.dp),
                            modifier = Modifier.padding(end = 12.dp, start = 2.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_mention),
                                contentDescription = null
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_emoji),
                                contentDescription = null
                            )
                        }

                    }

                )


            }

        }


    }
}


package com.innov.wakasinglebase.screens.following

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.core.DestinationRoute.CREATOR_PROFILE_ROUTE
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.data.model.ContentCreatorFollowingModel
import com.innov.wakasinglebase.screens.following.component.CreatorCard
import com.innov.wakasinglebase.ui.theme.DarkBlue

import com.innov.wakasinglebase.ui.theme.SubTextColor
import com.innov.wakasinglebase.R

/**
 * Created by innov Victor on 3/14/2023.
 */

@Composable
fun FollowingScreen(
    navController: NavController,
    //parentPagerState: PagerState,
    viewModel: FollowingViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        80.dp.Space()
        Text(
            text = stringResource(id = R.string.trending_creators),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            color= Color.White
        )
        24.dp.Space()
        Text(
            text = stringResource(id = R.string.follow_and_account_to_see),
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            textAlign = TextAlign.Center,
            color = SubTextColor
        )
        22.dp.Space()

       // if (parentPagerState.settledPage == 0) {
            viewState?.contentCreators?.let {
                VideoItem(
                    creatorList = it,
                    onclickUser = { userId -> navController.navigate("$CREATOR_PROFILE_ROUTE/$userId") })
            }
       // }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoItem(
    creatorList: List<ContentCreatorFollowingModel>,
    onclickUser: (userId: String) -> Unit
) {
    val pagerState = rememberPagerState()
    HorizontalPager(
        pageCount = creatorList.size,
        contentPadding = PaddingValues(horizontal = 54.dp),
        state = pagerState,
        beyondBoundsPageCount = 1
    ) {
        CreatorCard(
            page = it,
            pagerState = pagerState,
            item = creatorList[it],
            onClickUser = onclickUser,
            onClickFollow = {}
        )
    }
}


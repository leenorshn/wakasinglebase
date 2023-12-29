package com.innov.wakasinglebase.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.signin.PrivacyPolicyFooter
import com.innov.wakasinglebase.ui.theme.PrimaryColor

@Composable
fun UnAuthorizedInboxScreen(onClickSignup: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()

            .padding(horizontal = 28.dp),
        //.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        56.dp.Space()
        Text(
            text = stringResource(id = R.string.un_auth),
            style = MaterialTheme.typography.displaySmall.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
            )
        )
        20.dp.Space()
        Text(
            text = stringResource(id = R.string.unauth_message),
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(10))
        )
        20.dp.Space()
        Text(
            text = stringResource(id = R.string.login_in_to_your_existing_account),
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(10))
        )
        20.dp.Space()
        Icon(
            painter = painterResource(R.drawable.logo_tiktok_compose),
            contentDescription = "",
            //contentScale = ContentScale.Crop,
            modifier = Modifier.size(124.dp),
            tint = MaterialTheme.colorScheme.primary,
        )
        20.dp.Space()
//
        Text(text = "Just 2 min !",
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.weight(2f))
        // if(isLoading || currentUser == null){
        CustomButton(
            modifier = Modifier.width(340.dp),
            shape = RoundedCornerShape(16),
            buttonText = "Start now",
            containerColor = PrimaryColor,
            onClickButton = onClickSignup
        )
        Spacer(modifier = Modifier.weight(1f))
        PrivacyPolicyFooter()

    }
}
package com.innov.wakasinglebase.signin

//import com.innov.wakasinglebase.core.DestinationRoute.LOGIN_OR_SIGNUP_WITH_PHONE_ROUTE
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.common.CustomIconButton
import com.innov.wakasinglebase.common.DisplayAutoBackgroundSlider
import com.innov.wakasinglebase.core.AppContract.Annotate.ANNOTATED_PRIVACY_POLICY
import com.innov.wakasinglebase.core.AppContract.Annotate.ANNOTATED_TAG
import com.innov.wakasinglebase.core.AppContract.Annotate.ANNOTATED_TERMS_OF_SERVICE
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.ui.theme.SubTextColor
import com.innov.wakasinglebase.ui.theme.fontFamily

/**
 * Created by innov Victor on 3/27/2023.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthenticationScreen(
   // navController: NavController
state: SignInState,
onClickButton: () -> Unit
) {
val context= LocalContext.current
    LaunchedEffect(key1 = state.signInError, ){
        state.signInError?.let {error->
        Toast.makeText(context,error,Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()

    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            DisplayAutoBackgroundSlider()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 28.dp),
                //.verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                56.dp.Space()
                Text(
                    text = stringResource(id = R.string.login_or_sign_up),
                    style = MaterialTheme.typography.displayMedium.copy(
                        color=Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                )
                20.dp.Space()
                Text(
                    text = stringResource(id = R.string.login_in_to_your_existing_account),
                    color = Color(0xFFFF9800),
                    textAlign = TextAlign.Center,
                    modifier= Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(10))
                )
                Spacer(modifier = Modifier.weight(1f))
                AuthenticationButton(onClickButton)
                Spacer(modifier = Modifier.weight(1f))
                PrivacyPolicyFooter()
            }
        }
    }

}

@Composable
internal fun AuthenticationButton(onClickButton: () -> Unit) {

        CustomIconButton(
            buttonText = stringResource(id = R.string.continue_with_google),
            icon = R.drawable.ic_google,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            onClickButton()
        }


}

@Composable
fun PrivacyPolicyFooter() {
    val spanStyle = SpanStyle(
        fontSize = 13.sp,
        color = Color(0xFFFF5722),
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold
    )
    val annotatedString = buildAnnotatedString {
        append(stringResource(id = R.string.by_continuing_you_agree).plus(" "))
        pushStringAnnotation(
            tag = ANNOTATED_TAG, annotation = ANNOTATED_TERMS_OF_SERVICE
        )
        withStyle(
            style = spanStyle
        ) {
            append(stringResource(id = R.string.terms_of_service))
        }
        pop()
        append(" ".plus(stringResource(id = R.string.and_acknowledge_that_you_have)).plus(" "))
        pushStringAnnotation(
            tag = ANNOTATED_TAG, annotation = ANNOTATED_PRIVACY_POLICY
        )
        withStyle(
            style = spanStyle
        ) {
            append(stringResource(id = R.string.privacy_policy))
        }
        pop()
        append(" ".plus(stringResource(id = R.string.to_learn_how_we_collect)))

    }

    ClickableText(
        text = annotatedString, onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = ANNOTATED_TAG, start = offset, end = offset
            ).firstOrNull()?.let { annotation ->
                when (annotation.item) {
                    ANNOTATED_TERMS_OF_SERVICE -> {

                    }
                    ANNOTATED_PRIVACY_POLICY -> {

                    }
                }
            }
        }, style = TextStyle(
            color = SubTextColor,
            fontFamily = fontFamily,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    )
    20.dp.Space()
}
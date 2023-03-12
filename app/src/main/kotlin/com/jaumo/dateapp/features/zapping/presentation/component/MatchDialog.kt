package com.jaumo.dateapp.features.zapping.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jaumo.dateapp.R
import com.jaumo.dateapp.core.extensions.debugPlaceholderForImage
import com.jaumo.dateapp.features.zapping.domain.model.Gender
import com.jaumo.dateapp.features.zapping.domain.model.toProperStringFormat

@Composable
internal fun MatchDialog(
    onDismiss: () -> Unit,
    userPictureUrl: String,
    userName: String,
    gender: Gender
) {

    Dialog(onDismissRequest = onDismiss) {

        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        ) {

            val size = 50

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.White)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userPictureUrl)
                        .fallback(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_placeholder)
                        .build(),
                    placeholder = debugPlaceholderForImage(debugPreview = R.drawable.ic_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .requiredSize(size.dp)
                        .clip(
                            CircleShape
                        )
                )
                Text(
                    text = stringResource(id = R.string.match_text),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                )

                Text(
                    text = stringResource(
                        id = R.string.match_description,
                        userName,
                        gender.toProperStringFormat()
                    ),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
private fun MatchDialogExample() {
    MatchDialog(
        onDismiss = {},
        userPictureUrl = "https://user",
        userName = "Sienna",
        gender = Gender.MALE
    )
}
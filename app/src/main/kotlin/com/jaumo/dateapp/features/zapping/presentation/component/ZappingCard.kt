package com.jaumo.dateapp.features.zapping.presentation.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.jaumo.dateapp.R
import com.jaumo.dateapp.core.extensions.debugPlaceholderForImage
import com.jaumo.dateapp.features.zapping.domain.model.Gender
import com.jaumo.dateapp.features.zapping.domain.model.User
import com.jaumo.dateapp.ui.theme.CardBackGround

@Composable
internal fun ZappingCard(modifier: Modifier, user: User, getNewUser: () -> Unit) {
    val openDialog = remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Column {
            Column(
                modifier = Modifier
                    .background(CardBackGround)
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                val imageHeightDp = 350
                val imageWidthDp = 250

                Card(
                    modifier = Modifier
                        .requiredHeight(imageHeightDp.dp)
                        .requiredWidth(imageWidthDp.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp),
                    shape = RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 32.dp,
                        bottomEnd = 32.dp,
                        bottomStart = 0.dp
                    ),
                    elevation = 4.dp,
                    border = BorderStroke(
                        width = 6.dp,
                        color = Color.White
                    )
                ) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(user.userPicture)
                            .fallback(R.drawable.ic_placeholder)
                            .error(R.drawable.ic_placeholder)
                            .crossfade(true)
                            .build(),
                        placeholder = debugPlaceholderForImage(debugPreview = R.drawable.ic_placeholder),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .requiredHeight(imageHeightDp.dp)
                            .requiredWidth(imageWidthDp.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 16.dp),
            ) {
                Text(
                    text = user.lastName,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(start = 36.dp)
                )

                Text(
                    text = user.age.toString(),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(
                            start = 12.dp
                        )
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cross_ic),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(height = 45.dp, width = 45.dp)
                        .background(Color.Yellow)

                )
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.tick_ic),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(height = 45.dp, width = 45.dp)
                        .background(Color.Green)
                        .clickable {
                            if (!openDialog.value) {
                                openDialog.value = !openDialog.value
                            }
                        }
                )
            }
        }

        if (openDialog.value) {
            MatchDialog(
                onDismiss = {
                    getNewUser()
                    openDialog.value = !openDialog.value
                },
                userPictureUrl = user.userPicture,
                userName = user.lastName,
                gender = user.gender
            )
        }
    }
}

@Preview
@Composable
private fun PreviewZappingCard() {
    ZappingCard(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        user = User(
            lastName = "Sienna",
            age = 32,
            userPicture = "https://picture",
            thumbnail = "https://randomuser.me/api/portraits/thumb/men/75.jpg",
            gender = Gender.FEMALE
        ),
        getNewUser = {}
    )
}
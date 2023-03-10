package com.jaumo.dateapp.features.zapping.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.jaumo.dateapp.R
import com.jaumo.dateapp.core.extensions.debugPlaceholderForImage
import com.jaumo.dateapp.features.zapping.domain.model.User

@Composable
internal fun ZappingCard(modifier: Modifier, user: User) {
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
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                val imageHeightDp = 250
                val imageWidthDp = 150

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user.userPicture)
                        .fallback(R.drawable.ic_placeholder)
                        .transformations(
                            RoundedCornersTransformation(
                                topLeft = 16f, topRight = 16f, bottomLeft = 16f
                            )
                        )
                        .error(R.drawable.ic_placeholder)
                        .build(),
                    placeholder = debugPlaceholderForImage(debugPreview = R.drawable.ic_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .requiredHeight(imageHeightDp.dp)
                        .requiredWidth(imageWidthDp.dp)
                        .border(
                            width = 6.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(
                                topEnd = 16.dp,
                                topStart = 16.dp,
                                bottomEnd = 16.dp
                            )
                        )

                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
            ) {
                Text(
                    text = user.lastName,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )

                Text(
                    text = user.age.toString(),
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(
                            start = 4.dp
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
                )
            }
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
            userPicture = "https://picture"
        )
    )
}
package com.gerhardrvv.githubsearch.feature.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.gerhardrvv.githubsearch.designsystem.component.TextField
import com.gerhardrvv.githubsearch.designsystem.theme.AppTheme
import com.gerhardrvv.githubsearch.designsystem.util.AvatarShimmer
import com.gerhardrvv.githubsearch.feature.search.data.domain.Account
import githubsearch.composeapp.generated.resources.Res
import githubsearch.composeapp.generated.resources.ic_avatar_place_holder
import githubsearch.composeapp.generated.resources.repositories_count
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AccountListCard(
    account: Account,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 16.dp, vertical = 1.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.bg01),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            var imageLoadResult by remember {
                mutableStateOf<Result<Painter>?>(null)
            }

            val painter = rememberAsyncImagePainter(
                model = account.avatarUrl,
                onSuccess = {
                    imageLoadResult =
                        if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                            Result.success(it.painter)
                        } else {
                            Result.failure(Exception("Invalid image size"))
                        }
                },
                onError = {
                    it.result.throwable.printStackTrace()
                    imageLoadResult = Result.failure(it.result.throwable)
                }
            )

            when (val result = imageLoadResult) {
                null -> AvatarShimmer()
                else -> {
                    Image(
                        painter = if (result.isSuccess) painter else painterResource(Res.drawable.ic_avatar_place_holder),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(100.dp)
                            .aspectRatio(1f)
                            .clip(MaterialTheme.shapes.medium)
                            .border(
                                width = 2.dp,
                                color = AppTheme.colors.accent02,
                                shape = MaterialTheme.shapes.medium
                            ),
                        contentScale = if (result.isSuccess) ContentScale.Crop else ContentScale.Fit,
                    )
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    text = account.username,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = AppTheme.colors.text01
                )

                TextField(
                    text = stringResource(Res.string.repositories_count, account.repoCount),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = AppTheme.colors.text02
                )
            }
        }
    }
}

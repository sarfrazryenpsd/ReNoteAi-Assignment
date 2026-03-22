package com.ryen.renoteaiassignment.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ryen.renoteaiassignment.domain.model.User

@Composable
fun UserListItem(
    user: User,
    onUserClick: (Int) -> Unit,
    onFavoriteToggle: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(200),
        label = "favorite_scale"
    )
    val favoriteColor by animateColorAsState(
        targetValue = if (user.isFavorite)
            MaterialTheme.colorScheme.error
        else
            MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(300),
        label = "favorite_color"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onUserClick(user.id) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                onClick = { onFavoriteToggle(user.id) },
                modifier = Modifier.scale(favoriteScale)
            ) {
                Icon(
                    imageVector = if (user.isFavorite) Icons.Filled.Favorite
                    else Icons.Filled.FavoriteBorder,
                    contentDescription = if (user.isFavorite) "Remove from favorites"
                    else "Add to favorites",
                    tint = favoriteColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserListItemPrev() {
    MaterialTheme {
        UserListItem(
            user = User(
                id = 1,
                name = "Md Sarfraz Uddin",
                email = "sarfrazryen@gmail.com",
                phone = "7250406008",
                username = "sarfrazryen",
                isFavorite = false,
                city = "Patna",
                companyName = "Onesaz Pvt Ltd"
            ),
            onUserClick = {},
            onFavoriteToggle = {}
        )
    }
}
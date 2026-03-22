package com.ryen.renoteaiassignment.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ryen.renoteaiassignment.domain.model.User

@Composable
fun UserDetailItem(
    user: User,
    onFavoriteToggle: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = user.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Favorite toggle pill
        val isFav = user.isFavorite
        val favColor by animateColorAsState(
            targetValue = if (isFav) MaterialTheme.colorScheme.errorContainer
            else MaterialTheme.colorScheme.surfaceVariant,
            animationSpec = tween(300),
            label = "detail_fav_color"
        )
        val favContentColor by animateColorAsState(
            targetValue = if (isFav) MaterialTheme.colorScheme.onErrorContainer
            else MaterialTheme.colorScheme.onSurfaceVariant,
            animationSpec = tween(300),
            label = "detail_fav_content"
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(favColor)
                .clickable { onFavoriteToggle(user.id) }
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = if (isFav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = null,
                    tint = favContentColor,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = if (isFav) "Saved to favorites" else "Add to favorites",
                    style = MaterialTheme.typography.labelMedium,
                    color = favContentColor
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Info rows
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                DetailRow(icon = Icons.Outlined.Email,     label = "Email",   value = user.email)
                DetailRow(icon = Icons.Outlined.Phone,     label = "Phone",   value = user.phone)
                DetailRow(icon = Icons.Outlined.Business,  label = "Company", value = user.companyName)
                DetailRow(icon = Icons.Outlined.LocationOn,label = "City",    value = user.city, isLast = true)
            }
        }
    }
}

@Composable
private fun DetailRow(
    icon: ImageVector,
    label: String,
    value: String,
    isLast: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
    if (!isLast) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.outlineVariant)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserDetailItemPrev() {
    MaterialTheme {
        UserDetailItem(
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
            onFavoriteToggle = {}
        )
    }
}
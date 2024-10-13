@file:OptIn(ExperimentalMaterial3Api::class)

package com.sample.kinopoisk.core.ui.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.sample.kinopoisk.core.ui.icon.AppIcons
import com.sample.kinopoisk.core.ui.theme.AppTheme

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    actionIcon: ImageVector? = null,
    actionIconContentDescription: String? = null,
    title: String? = null,
    navigationIcon: ImageVector? = null,
    navigationIconContentDescription: String? = null,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            if (title != null) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    maxLines = 1,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = navigationIconContentDescription,
                    )
                }
            }
        },
        actions = {
            if (actionIcon != null) {
                IconButton(onClick = onActionClick) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = actionIconContentDescription,
                    )
                }
            }
        },
        colors = colors,
        modifier = modifier.testTag("TopAppBar"),
    )
}

@Composable
fun AppTopBarColored(
    modifier: Modifier = Modifier,
    actionIcon: ImageVector? = null,
    actionIconContentDescription: String? = null,
    title: String? = null,
    navigationIcon: ImageVector? = null,
    navigationIconContentDescription: String? = null,
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val titleColor = MaterialTheme.colorScheme.onPrimary

    AppTopBar(
        actionIcon = actionIcon,
        actionIconContentDescription = actionIconContentDescription,
        title = title,
        navigationIcon = navigationIcon,
        navigationIconContentDescription = navigationIconContentDescription,
        modifier = modifier,
        onNavigationClick = onNavigationClick,
        onActionClick = onActionClick,
        colors = TopAppBarColors(
            containerColor = primaryColor,
            scrolledContainerColor = primaryColor,
            navigationIconContentColor = titleColor,
            titleContentColor = titleColor,
            actionIconContentColor = titleColor
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun AppTopBarPreview() {
    AppTheme {
        AppTopBar(
            title = stringResource(android.R.string.untitled),
            actionIcon = AppIcons.Empty,
            actionIconContentDescription = "Action icon",
            navigationIcon = AppIcons.ArrowBack,
            navigationIconContentDescription = "Navigation icon",
        )
    }
}

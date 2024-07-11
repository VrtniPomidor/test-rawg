package com.example.testrawg.presentation.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.testrawg.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationItem: MenuItem? = null,
    actionItem: MenuItem? = null,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title, textAlign = TextAlign.Center) },
        navigationIcon = {
            navigationItem?.let { item ->
                MenuIconButton(item)
            }
        },
        actions = {
            actionItem?.let { item ->
                MenuIconButton(item)
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun MenuIconButton(item: MenuItem) {
    IconButton(onClick = item.onClick) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.iconContentDescription?.let { stringResource(id = it) },
            tint = MaterialTheme.colorScheme.onSurface,
        )
    }
}

sealed class MenuItem(
    val icon: ImageVector,
    val iconContentDescription: Int? = null,
    open val onClick: () -> Unit = {},
) {
    data class Back(
        override val onClick: () -> Unit
    ) : MenuItem(
        icon = RawgIcons.ArrowBack,
        iconContentDescription = R.string.arrow_back_content_desc,
        onClick = onClick
    )

    data class Search(
        override val onClick: () -> Unit
    ) : MenuItem(
        icon = RawgIcons.Search,
        iconContentDescription = R.string.search_icon_content_description,
        onClick = onClick
    )

    data class Settings(
        override val onClick: () -> Unit
    ) : MenuItem(
        icon = RawgIcons.Settings,
        iconContentDescription = R.string.settings_icon_content_description,
        onClick = onClick
    )
}

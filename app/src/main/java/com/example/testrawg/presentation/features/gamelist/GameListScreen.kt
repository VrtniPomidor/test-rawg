package com.example.testrawg.presentation.features.gamelist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.testrawg.R
import com.example.testrawg.presentation.components.TitleBar
import com.example.testrawg.presentation.navigation.GameList

fun NavController.navigateToGameListScreen(navOptions: NavOptions) = navigate(GameList, navOptions)

@Composable
fun GameListScreen(
    onGameDetailsClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TitleBar(
            title = stringResource(id = R.string.games_list_title),
            actionIcon = Icons.Filled.Settings,
            actionIconContentDescription = stringResource(id = R.string.settings_icon_content_description),
            onActionClick = onSettingsClicked
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onGameDetailsClicked) {
            Text("Go To Game Details Screen")
        }
    }
}

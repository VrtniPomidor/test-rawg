package com.example.testrawg.presentation.features.gamelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavOptions
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
        verticalArrangement = Arrangement.Center
    ) {
        Text("Game List Screen")
        Button(onClick = onGameDetailsClicked) {
            Text("Go To Game Details Screen")
        }
        Button(onClick = onSettingsClicked) {
            Text("Go To Settings Screen")
        }
    }
}

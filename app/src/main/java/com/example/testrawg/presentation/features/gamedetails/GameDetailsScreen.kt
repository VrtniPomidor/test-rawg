package com.example.testrawg.presentation.features.gamedetails

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
import com.example.testrawg.presentation.navigation.GameDetails

fun NavController.navigateToGameDetails(navOptions: NavOptions? = null) =
    navigate(GameDetails, navOptions)

@Composable
fun GameDetailsScreen(
    onBackClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Game Details Screen")

        Button(onClick = onBackClicked) {
            Text("Go Back")
        }
    }
}

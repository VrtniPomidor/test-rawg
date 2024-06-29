package com.example.testrawg.presentation.features.settings

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
import com.example.testrawg.presentation.navigation.Settings

fun NavController.navigateToSettingsScreen(navOptions: NavOptions? = null) =
    navigate(Settings, navOptions)

@Composable
fun SettingsScreen(
    onBackClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Settings Screen")

        Button(onClick = onBackClicked) {
            Text("Go Back")
        }
    }
}

package com.example.testrawg.presentation.features.onboarding

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
import com.example.testrawg.presentation.navigation.Onboarding

fun NavController.navigateToOnboardingScreen(navOptions: NavOptions) =
    navigate(Onboarding, navOptions)

@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Onboarding Screen")

        Button(onClick = onFinishOnboarding) {
            Text("Go To Game List Screen")
        }
    }
}

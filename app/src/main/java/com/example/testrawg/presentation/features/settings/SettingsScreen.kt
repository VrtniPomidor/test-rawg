package com.example.testrawg.presentation.features.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.testrawg.R
import com.example.testrawg.presentation.components.AdaptiveButton
import com.example.testrawg.presentation.components.MenuItem
import com.example.testrawg.presentation.components.TitleBar
import com.example.testrawg.presentation.navigation.Settings
import com.example.testrawg.presentation.navigation.safeNavigate

fun NavController.navigateToSettingsScreen(navOptions: NavOptions? = null) =
    safeNavigate(Settings, navOptions)

@Composable
fun SettingsScreen(
    onShowGenres: () -> Unit,
    onBackClicked: () -> Unit,
) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val state by viewModel.settingsUiState.collectAsStateWithLifecycle()

    SettingsContent(
        state = state,
        resetOnboarding = viewModel::resetOnboarding,
        onShowGenres = onShowGenres,
        onBackClicked = onBackClicked
    )
}

@Composable
private fun SettingsContent(
    state: SettingsState,
    onShowGenres: () -> Unit,
    resetOnboarding: () -> Unit,
    onBackClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TitleBar(
            stringResource(R.string.settings_screen_title),
            navigationItem = MenuItem.Back(
                onClick = onBackClicked
            ),
        )

        if (state.showResetOnboarding) {
            AdaptiveButton(
                isEnabled = state.isEnabledOnboarding,
                onClick = resetOnboarding
            ) {
                Text(stringResource(R.string.reset_onboarding_button_text))
            }
            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }
        AdaptiveButton(
            onClick = onShowGenres
        ) {
            Text("Show Genre Selection")
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

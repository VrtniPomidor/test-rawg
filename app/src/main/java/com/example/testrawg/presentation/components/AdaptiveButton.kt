package com.example.testrawg.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Adaptive button for different screen sizes
 *
 */
@Composable
fun AdaptiveButton(
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .widthIn(max = 364.dp)
            .fillMaxWidth(),
        enabled = isEnabled,
        onClick = onClick,
    ) {
        content()
    }
}

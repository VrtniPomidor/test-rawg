package com.example.testrawg.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.height(240.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Error")
    }
}

package com.example.testrawg.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.testrawg.R

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    retry: () -> Unit
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .heightIn(min = 240.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.something_went_wrong_title))
        Spacer(modifier = Modifier.height(8.dp))
        Button(modifier = Modifier.widthIn(min = 160.dp), onClick = retry) {
            Text(stringResource(R.string.retry_button_text))
        }
    }
}

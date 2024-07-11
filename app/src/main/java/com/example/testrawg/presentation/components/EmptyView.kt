package com.example.testrawg.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testrawg.R

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    message: String = stringResource(id = R.string.empty_label)
) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            imageVector = RawgIcons.Search,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(message)
    }
}

@Preview
@Composable
private fun EmptyViewPreview() {
    EmptyView()
}

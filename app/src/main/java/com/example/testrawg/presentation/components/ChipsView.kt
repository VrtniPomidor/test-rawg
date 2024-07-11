package com.example.testrawg.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Show chips from a list of strings in a multirow layout with possible modifier adjustments
 *
 * @param modifier Modifier to edit FlowRow size for instance
 * @param itemCount Int number of items
 * @param onText (Int) -> String based on index provide text to show in a chip
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipsView(
    modifier: Modifier = Modifier,
    itemCount: Int,
    onText: (Int) -> String
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        for (i in 0..<itemCount) {
            Text(
                onText(i),
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CircleShape
                    )
                    .padding(vertical = 3.dp, horizontal = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ChipsViewPreview() {
    val testData = listOf("FirstChip", "SecondChip", "ThirdChip")
    Box(modifier = Modifier.fillMaxSize()) {
        ChipsView(
            modifier = Modifier.fillMaxSize(),
            itemCount = testData.size,
            onText = { index -> testData[index] }
        )
    }
}

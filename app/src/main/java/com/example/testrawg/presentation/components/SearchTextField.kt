package com.example.testrawg.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testrawg.R


@Composable
fun SearchTextField(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit,
    onBack: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchTriggered(searchQuery)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            keyboardController?.hide()
            onBack()
        }) {
            Icon(
                imageVector = RawgIcons.ArrowBack,
                contentDescription = stringResource(id = R.string.arrow_back_content_desc),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
        TextField(
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            leadingIcon = {
                Icon(
                    imageVector = RawgIcons.Search,
                    contentDescription = stringResource(
                        id = R.string.search_icon_content_description,
                    ),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onSearchQueryChanged("")
                        },
                    ) {
                        Icon(
                            imageVector = RawgIcons.Close,
                            contentDescription = stringResource(
                                id = R.string.search_clear_search_text_content_description,
                            ),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            },
            onValueChange = {
                if ("\n" !in it.text) onSearchQueryChanged(it.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .focusRequester(focusRequester)
                .onKeyEvent {
                    if (it.key == Key.Enter) {
                        onSearchExplicitlyTriggered()
                        true
                    } else {
                        false
                    }
                },
            shape = RoundedCornerShape(32.dp),
            value = TextFieldValue(
                text = searchQuery,
                selection = TextRange(searchQuery.length)
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchExplicitlyTriggered()
                },
            ),
            maxLines = 1,
            singleLine = true,
        )
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview
@Composable
private fun SearchTextFieldPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        SearchTextField(
            searchQuery = "Initial Search",
            onSearchQueryChanged = {},
            onSearchTriggered = {},
            onBack = {}
        )
    }
}

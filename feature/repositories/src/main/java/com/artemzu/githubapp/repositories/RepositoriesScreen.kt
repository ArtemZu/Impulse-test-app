package com.artemzu.githubapp.repositories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.artemzu.githubapp.common.network.browser.launchCustomChromeTab
import com.artemzu.githubapp.repositories.model.RepositoriesUiState
import com.artemzu.githubapp.ui.RepositoryItem
import com.artemzu.githubapp.ui.data.RepositoryUiItem

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalComposeUiApi::class)
@Composable
fun RepositoriesScreen(
    viewModel: RepositoriesViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.background.toArgb()

    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.event.collect { event ->
                when (event) {
                    is RepositoriesViewModel.Event.OpenBrowser -> {
                        launchCustomChromeTab(context, event.uri, backgroundColor)
                    }

                    is RepositoriesViewModel.Event.ShowError -> {
                        keyboardController?.hide()
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.backend_error),
                            duration = SnackbarDuration.Short,
                        )
                    }
                }
            }
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RepositoriesScreenContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        loadNextItems = viewModel::loadNextItems,
        onValueChange = viewModel::onSearchTextChanged,
        onItemClick = viewModel::onItemClicked,
        onLogoutClicked = viewModel::logout
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepositoriesScreenContent(
    uiState: RepositoriesUiState,
    snackbarHostState: SnackbarHostState,
    loadNextItems: () -> Unit,
    onValueChange: (text: String) -> Unit,
    onItemClick: (item: RepositoryUiItem) -> Unit,
    onLogoutClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                text = uiState.searchText,
                isLoading = uiState.isLoading,
                onClearTextClicked = { onValueChange("") },
                onValueChange = onValueChange,
                onLogoutClicked = onLogoutClicked
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (uiState.repositoriesList.isEmpty() && !uiState.isLoading) {
                EmptyState()
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.repositoriesList.size) { i ->
                    if (i >= uiState.repositoriesList.size - 3 && !uiState.endReached && !uiState.isLoading) {
                        loadNextItems()
                    }

                    val item = uiState.repositoriesList[i]
                    RepositoryItem(item = item, onItemClick = onItemClick)
                }

                item {
                    if (uiState.isLoading && uiState.repositoriesList.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.try_to_find_something),
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    text: String,
    isLoading: Boolean,
    onClearTextClicked: () -> Unit,
    onValueChange: (text: String) -> Unit,
    onLogoutClicked: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    TopAppBar(
        actions = {
            IconButton(onClick = onLogoutClicked) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        title = {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                label = { Text(text = "Search repository") },
                leadingIcon = {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            contentDescription = null,
                            imageVector = Icons.Rounded.Search
                        )
                    }
                },
                trailingIcon = {
                    if (text.isNotBlank()) {
                        IconButton(
                            onClick = {
                                focusManager.clearFocus()
                                onClearTextClicked()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    )
}
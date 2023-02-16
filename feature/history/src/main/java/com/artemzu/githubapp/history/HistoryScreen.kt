package com.artemzu.githubapp.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.artemzu.githubapp.common.network.browser.launchCustomChromeTab
import com.artemzu.githubapp.history.model.HistoryUiState
import com.artemzu.githubapp.ui.RepositoryItem
import com.artemzu.githubapp.ui.data.RepositoryUiItem

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.background.toArgb()
    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.event.collect { event ->
                when (event) {
                    is HistoryViewModel.Event.OpenBrowser -> {
                        launchCustomChromeTab(context, event.uri, backgroundColor)
                    }
                }
            }
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HistoryScreenContent(
        uiState = uiState,
        onItemClick = viewModel::onItemClicked
    )
}

@Composable
private fun HistoryScreenContent(
    uiState: HistoryUiState,
    onItemClick: (item: RepositoryUiItem) -> Unit
) {

    if (uiState.historyList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.nothing_to_show),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    LazyColumn(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(uiState.historyList) { item ->
            RepositoryItem(item = item, onItemClick = onItemClick)
        }
    }
}
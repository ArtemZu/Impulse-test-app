package com.artemzu.githubapp.history

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemzu.githubapp.domain.history.GetSavedRepositoriesUseCase
import com.artemzu.githubapp.history.model.HistoryUiState
import com.artemzu.githubapp.history.model.asRepositoryItem
import com.artemzu.githubapp.ui.data.RepositoryUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    getSavedRepositoriesUseCase: GetSavedRepositoriesUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    val uiState: StateFlow<HistoryUiState> = getSavedRepositoriesUseCase()
        .map {
            HistoryUiState(
                isLoading = false,
                historyList = it.map { repository -> repository.asRepositoryItem() }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HistoryUiState()
        )

    fun onItemClicked(repositoryUiItem: RepositoryUiItem) {
        viewModelScope.launch {
            val resourceUrl = Uri.parse(repositoryUiItem.url)
            _event.emit(Event.OpenBrowser(resourceUrl))
        }
    }

    sealed interface Event {
        data class OpenBrowser(val uri: Uri) : Event
    }
}
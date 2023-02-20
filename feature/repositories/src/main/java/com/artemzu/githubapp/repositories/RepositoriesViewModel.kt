package com.artemzu.githubapp.repositories

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemzu.githubapp.data.model.Repository
import com.artemzu.githubapp.domain.auth.LogoutUseCase
import com.artemzu.githubapp.domain.history.SaveRepositoryUseCase
import com.artemzu.githubapp.domain.repositories.GetSavedRepositoriesIds
import com.artemzu.githubapp.domain.repositories.SearchRepositoriesUseCase
import com.artemzu.githubapp.repositories.model.RepositoriesUiState
import com.artemzu.githubapp.repositories.model.asRepositoryItem
import com.artemzu.githubapp.repositories.paging.RepositoriesPaginator
import com.artemzu.githubapp.ui.data.RepositoryUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val searchRepositoriesUseCase: SearchRepositoriesUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val saveRepositoryUseCase: SaveRepositoryUseCase,
    getSavedRepositoriesIds: GetSavedRepositoriesIds
) : ViewModel() {

    private val _uiState = MutableStateFlow(RepositoriesUiState())
    val uiState: StateFlow<RepositoriesUiState> =
        combine(_uiState, getSavedRepositoriesIds.invoke()) { uiState, savedIdsList ->
            val updatedList = uiState.repositoriesList.map { item ->
                if (savedIdsList.contains(item.id)) {
                    item.copy(isShown = true)
                } else {
                    item
                }
            }
            uiState.copy(repositoriesList = updatedList)
        }.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RepositoriesUiState()
        )

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    private var searchJob: Job? = null
    private val debouncePeriod: Long = 600

    private val paginator = RepositoriesPaginator(
        initialKey = _uiState.value.page,
        onLoadUpdated = { isLoading ->
            _uiState.update {
                it.copy(isLoading = isLoading)
            }
        },
        onRequest = { newPage ->
            searchRepositoriesUseCase(_uiState.value.searchText, newPage)
        },

        getNextKey = {
            _uiState.value.page + 2
        },
        onError = {
            _uiState.update { it.copy(isLoading = false) }
            _event.emit(Event.ShowError)
        },
        onSuccess = { items: List<Repository>, newKey: Int, isNewRequest: Boolean ->
            val repositoriesItemsList = items.map { it.asRepositoryItem() }
            _uiState.update {
                it.copy(
                    repositoriesList = if (isNewRequest) repositoriesItemsList else it.repositoriesList + repositoriesItemsList,
                    isLoading = false,
                    page = newKey,
                    endReached = items.isEmpty()
                )
            }
        },
    )

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    fun onSearchTextChanged(searchText: String) {
        searchJob?.cancel()
        paginator.reset()
        searchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    searchText = searchText,
                    page = 1
                )
            }
            if (searchText.isNotBlank()) {
                delay(debouncePeriod)
                searchRepositories(isNewRequest = true)
            } else {
                _uiState.update { it.copy(repositoriesList = emptyList()) }
            }
        }
    }

    fun onItemClicked(item: RepositoryUiItem) {
        viewModelScope.launch {
            saveRepositoryUseCase(item.asRepositoryItem())

            val resourceUrl = Uri.parse(item.url)
            _event.emit(Event.OpenBrowser(resourceUrl))
        }
    }

    fun loadNextItems() {
        viewModelScope.launch {
            searchRepositories()
        }
    }

    private suspend fun searchRepositories(isNewRequest: Boolean = false) {
        paginator.loadNextItems(isNewRequest)
    }

    sealed interface Event {
        data class OpenBrowser(val uri: Uri) : Event
        object ShowError : Event
    }
}
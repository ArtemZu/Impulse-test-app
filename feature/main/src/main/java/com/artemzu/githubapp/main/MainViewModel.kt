package com.artemzu.githubapp.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemzu.githubapp.domain.auth.IsUserAuthorizedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            isUserAuthorizedUseCase().collect { isAuthorized ->
                if (!isAuthorized) {
                    _event.emit(Event.Logout)
                }
            }
        }
    }

    sealed interface Event {
        object Logout : Event
    }
}
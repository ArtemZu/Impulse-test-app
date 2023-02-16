package com.artemzu.auth

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemzu.githubapp.domain.auth.GetAuthCodeUseCase
import com.artemzu.githubapp.domain.auth.SaveCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getAuthCodeUseCase: GetAuthCodeUseCase,
    private val saveCodeUseCase: SaveCodeUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<Event>(replay = 1)
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            getAuthCodeUseCase().collect {
                if (it != null) {
                    _event.emit(Event.CloseActivity)
                }
            }
        }
    }

    fun handleUri(uri: Uri?) {
        viewModelScope.launch {
            saveCodeUseCase(uri)
        }
    }

    sealed interface Event {
        object CloseActivity : Event
    }
}
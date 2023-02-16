package com.artemzu.githubapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemzu.githubapp.domain.auth.IsUserAuthorizedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    isUserAuthorizedUseCase: IsUserAuthorizedUseCase
) : ViewModel() {

    val isUserAuthorized: StateFlow<Boolean?> = isUserAuthorizedUseCase()
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )
}
package com.artemzu.auth.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemzu.auth.BuildConfig
import com.artemzu.auth.R
import com.artemzu.githubapp.designsystem.components.UiText
import com.artemzu.githubapp.domain.auth.AuthorizationUseCase
import com.artemzu.githubapp.domain.auth.GetAuthCodeUseCase
import com.artemzu.githubapp.domain.utils.IsDeviceOnlineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    private val getAuthCodeUseCase: GetAuthCodeUseCase,
    private val authorizationUseCase: AuthorizationUseCase,
    isDeviceOnlineUseCase: IsDeviceOnlineUseCase
) : ViewModel() {
    private val _authUiState = MutableStateFlow(AuthUiState())
    val authUiState = _authUiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    private val _isOnline = isDeviceOnlineUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = true
        )

    init {
        viewModelScope.launch {
            combine(
                getAuthCodeUseCase(),
                _isOnline
            ) { authCode, isOnline ->
                _authUiState.update { uiState -> uiState.copy(shouldShowConnectionError = !isOnline) }

                if (isOnline && authCode != null) {
                    _authUiState.update { uiState ->
                        uiState.copy(
                            isSignInButtonEnabled = false,
                            isLoading = true
                        )
                    }

                    val result = authorizationUseCase(
                        clientId = BuildConfig.GITHUB_CLIENT_ID,
                        clientSecret = BuildConfig.GITHUB_CLIENT_SECRET,
                        code = authCode
                    )

                    when (result) {
                        is AuthorizationUseCase.RequestStatus.CommonError -> {
                            _authUiState.update { uiState ->
                                uiState.copy(
                                    errorMessage = UiText.StringResources(R.string.common_error),
                                    isLoading = false,
                                    isSignInButtonEnabled = true
                                )
                            }
                        }

                        is AuthorizationUseCase.RequestStatus.ErrorWithMessage -> {
                            _authUiState.update { uiState ->
                                uiState.copy(
                                    errorMessage = UiText.DynamicString(result.errorMessage),
                                    isLoading = false,
                                    isSignInButtonEnabled = true
                                )
                            }
                        }

                        is AuthorizationUseCase.RequestStatus.Success -> _event.emit(Event.ShowMainGraph)
                    }
                }

            }.collect()
        }
    }

    fun signInClicked() {
        viewModelScope.launch {
            if (_isOnline.value) {
                _authUiState.update {
                    it.copy(
                        isLoading = true,
                        isSignInButtonEnabled = false
                    )
                }
                val uri = Uri.Builder()
                    .scheme("https")
                    .authority("github.com")
                    .appendPath("login")
                    .appendPath("oauth")
                    .appendPath("authorize")
                    .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
                    .appendQueryParameter("scope", "repo")
                    .appendQueryParameter(
                        "redirect_uri",
                        "artemzu://callback"
                    ) //also used in manifest
                    .build()
                _event.emit(Event.SignIn(uri))
            }
        }
    }
}

sealed interface Event {
    object ShowMainGraph : Event
    data class SignIn(val uri: Uri) : Event
}

data class AuthUiState(
    val isLoading: Boolean = false,
    val isSignInButtonEnabled: Boolean = true,
    val errorMessage: UiText? = null,
    val shouldShowConnectionError: Boolean = false
)
package com.artemzu.auth.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.artemzu.auth.R
import com.artemzu.githubapp.designsystem.GitHubAppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun AuthScreen(
    viewModel: AuthScreenViewModel = hiltViewModel(),
    navigateToMainGraph: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.authUiState.collectAsStateWithLifecycle()

    val notConnectedMessage = stringResource(R.string.not_connected)
    LaunchedEffect(uiState.shouldShowConnectionError) {
        if (uiState.shouldShowConnectionError) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }

    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.event.collect { event ->
                when (event) {
                    is Event.ShowMainGraph -> navigateToMainGraph()
                    is Event.SignIn -> context.startActivity(Intent(Intent.ACTION_VIEW, event.uri))
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddings ->
        AuthScreenContent(
            modifier = Modifier.padding(paddings),
            uiState = uiState,
            signInClicked = viewModel::signInClicked
        )
    }
}

@Composable
private fun AuthScreenContent(
    modifier: Modifier = Modifier,
    uiState: AuthUiState,
    signInClicked: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Header()
        Spacer(modifier = Modifier.weight(1f))
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        if (uiState.errorMessage != null) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = uiState.errorMessage.asString(),
                style = MaterialTheme.typography.titleMedium
            )
        }
        SignInButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            isSignInButtonEnabled = uiState.isSignInButtonEnabled,
            signInClicked = signInClicked
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomEnd = 72.dp))
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 56.dp)
        ) {
            Text(
                text = stringResource(R.string.sign_in_header_1),
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = stringResource(R.string.sign_in_header_2),
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Composable
private fun SignInButton(
    modifier: Modifier = Modifier,
    isSignInButtonEnabled: Boolean,
    signInClicked: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(36.dp),
        enabled = isSignInButtonEnabled,
        onClick = signInClicked
    ) {
        Text(
            text = stringResource(R.string.sign_in),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {
    GitHubAppTheme {
        AuthScreenContent(
            uiState = AuthUiState(isLoading = true),
            signInClicked = {}
        )
    }
}

@Preview
@Composable
private fun AuthSignInButtonDisabledPreview() {
    GitHubAppTheme {
        SignInButton(
            isSignInButtonEnabled = false,
            signInClicked = {}
        )
    }
}
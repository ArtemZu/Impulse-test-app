package com.artemzu.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val githubCode = stringPreferencesKey("github_code")
private val githubToken = stringPreferencesKey("github_token")

class AuthDataStore @Inject constructor(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "github_data")

    suspend fun saveCode(code: String) {
        context.dataStore.edit { settings ->
            settings[githubCode] = code
        }
    }

    fun getCodeFlow(): Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[githubCode]
        }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { settings ->
            settings[githubToken] = token
        }
    }

    fun getTokenFlow(): Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[githubToken]
        }

    suspend fun removeCode() {
        context.dataStore.edit {
            it.remove(githubCode)
        }
    }

    suspend fun removeAllData() {
        context.dataStore.edit {
            it.clear()
        }
    }
}
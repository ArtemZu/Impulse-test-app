package com.artemzu.githubapp.data.di

import com.artemzu.githubapp.data.repository.auth.AuthRepository
import com.artemzu.githubapp.data.repository.auth.AuthRepositoryImpl
import com.artemzu.githubapp.data.repository.history.HistoryRepository
import com.artemzu.githubapp.data.repository.history.HistoryRepositoryImpl
import com.artemzu.githubapp.data.repository.repositories.RepositoriesRepository
import com.artemzu.githubapp.data.repository.repositories.RepositoriesRepositoryImpl
import com.artemzu.githubapp.data.utils.ConnectivityManagerNetworkMonitor
import com.artemzu.githubapp.data.utils.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindAuthRepository(
        authRepository: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    fun bindRepositoriesRepository(
        authRepository: RepositoriesRepositoryImpl
    ): RepositoriesRepository

    @Binds
    @Singleton
    fun bindHistoryRepository(
        historyRepository: HistoryRepositoryImpl
    ): HistoryRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}
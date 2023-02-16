package com.artemzu.githubapp.network.di

import com.artemzu.githubapp.network.BuildConfig
import com.artemzu.githubapp.network.retrofit.auth.AuthNetwork
import com.artemzu.githubapp.network.retrofit.auth.AuthNetworkApi
import com.artemzu.githubapp.network.retrofit.auth.AuthNetworkImpl
import com.artemzu.githubapp.network.retrofit.repositories.RepositoriesNetwork
import com.artemzu.githubapp.network.retrofit.repositories.RepositoriesNetworkApi
import com.artemzu.githubapp.network.retrofit.repositories.RepositoriesNetworkImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                },
            )
            .build()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(client: OkHttpClient, networkJson: Json): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .addConverterFactory(
                @OptIn(ExperimentalSerializationApi::class)
                networkJson.asConverterFactory("application/json".toMediaType()),
            )

    @Provides
    @Singleton
    @Auth
    fun provideRetrofitAuth(
        retrofit: Retrofit.Builder
    ): Retrofit = retrofit
        .baseUrl(BuildConfig.AUTH_BASE_URL)
        .build()

    @Provides
    @Singleton
    fun authApiService(@Auth retrofit: Retrofit): AuthNetworkApi =
        retrofit.create(AuthNetworkApi::class.java)

    @Provides
    @Singleton
    @Main
    fun provideRetrofitMain(
        retrofit: Retrofit.Builder
    ): Retrofit = retrofit
        .baseUrl(BuildConfig.MAIN_BASE_URL)
        .build()

    @Provides
    @Singleton
    fun repositoriesApiService(@Main retrofit: Retrofit): RepositoriesNetworkApi =
        retrofit.create(RepositoriesNetworkApi::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        fun bindsAuthNetwork(authNetworkImpl: AuthNetworkImpl): AuthNetwork

        @Binds
        fun bindsRepositoriesNetwork(repositoriesNetworkImpl: RepositoriesNetworkImpl): RepositoriesNetwork
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Auth

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Main
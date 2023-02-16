package com.artemzu.database.di

import android.content.Context
import com.artemzu.database.components.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun buildDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.buildDatabase(context)

    @Provides
    fun provideRepositoryDao(db: AppDatabase) = db.repositoryDao()
}
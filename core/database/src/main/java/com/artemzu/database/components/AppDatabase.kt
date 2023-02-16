package com.artemzu.database.components

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.artemzu.database.entity.RepositoryDao
import com.artemzu.database.entity.RepositoryEntity

private const val DATABASE_NAME = "AppDatabase"

@Database(
    entities = [RepositoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun buildDatabase(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun repositoryDao(): RepositoryDao
}
package com.artemzu.database.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts: RepositoryEntity)

    @Query("SELECT * FROM RepositoryEntity")
    fun getCachedRepositories(): Flow<List<RepositoryEntity>>

    @Query("DELETE FROM RepositoryEntity WHERE id IN (SELECT id FROM RepositoryEntity LIMIT 1 OFFSET 20)")
    suspend fun removeOldData()
}
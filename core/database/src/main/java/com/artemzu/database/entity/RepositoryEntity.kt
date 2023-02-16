package com.artemzu.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepositoryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String?,
    val language: String?,
    val url: String,
    val ownerAvatarUrl: String,
    val ownerLogin: String
)
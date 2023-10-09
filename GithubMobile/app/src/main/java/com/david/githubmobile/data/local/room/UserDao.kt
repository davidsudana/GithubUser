package com.david.githubmobile.data.local.room

import androidx.room.*
import com.david.githubmobile.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY username ASC")
    suspend fun getFavorites(): List<UserEntity>

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun isFavorite(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(user: UserEntity)

    @Delete
    suspend fun deleteFromFavorite(user: UserEntity)
}
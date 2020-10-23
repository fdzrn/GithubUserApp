package com.codelab.githubuser.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.codelab.githubuser.model.DetailUser

@Dao
interface FavoriteDAO {
    @Insert(onConflict = REPLACE)
    suspend fun addFavorite(detailUser: DetailUser)

    @Query("SELECT * FROM user_favorite_table ORDER BY username ASC")
    fun selectAllData(): LiveData<List<DetailUser>>

    @Query("SELECT * FROM user_favorite_table WHERE id = :userID")
    fun getUserByID(userID:Int): LiveData<List<DetailUser>>

    @Delete
    suspend fun removeFavorite(detailUser: DetailUser)

    // call in provider
    @Query("SELECT * FROM user_favorite_table")
    fun cursorGetAll(): Cursor
}
package com.elbek.worldnews.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.elbek.worldnews.models.UserNews

@Dao
interface UserDao {
    @Insert
    fun insertNews(userNews: UserNews)
    @Query("SELECT * FROM worldNews")
    fun getAllNews():List<UserNews>
    @Delete
    fun deleteNews(userNews: UserNews)

}
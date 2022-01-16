package com.elbek.worldnews.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elbek.worldnews.models.UserNews
import kotlinx.coroutines.internal.synchronized

@Database(
    entities = [UserNews::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDao(): UserDao

    companion object {
        private var database: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "news.db"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return database
        }
    }
}
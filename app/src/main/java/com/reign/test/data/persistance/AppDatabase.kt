package com.reign.test.data.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.reign.test.data.models.Article

@Database(entities = [Article::class], version = 1, exportSchema = true)
@TypeConverters(IntegerListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val articleDao: ArticleDao

}
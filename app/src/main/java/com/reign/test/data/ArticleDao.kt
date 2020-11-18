package com.reign.test.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reign.test.data.models.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM Article")
    fun findAll(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(article: List<Article?>)
}
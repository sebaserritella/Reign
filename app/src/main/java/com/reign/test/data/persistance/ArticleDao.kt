package com.reign.test.data.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reign.test.data.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticleList(articles: Article)

    @Query("SELECT * FROM Article WHERE id = :id_")
    fun getArticle(id_: Long): Article

    @Query("SELECT * FROM Article")
    fun getArticlesList(): List<Article>
}
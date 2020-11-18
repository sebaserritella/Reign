package com.reign.test.data.repositories

import com.reign.test.data.models.Article
import com.reign.test.data.services.ArticlesService
import retrofit2.Response

class ArticleRemoteDataSource(
    private val characterService: ArticlesService
) {
    suspend fun getArticles(): Response<Article> {
        return characterService.getArticleByDateAsync()
    }
}
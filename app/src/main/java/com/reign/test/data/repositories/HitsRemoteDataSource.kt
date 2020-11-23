package com.reign.test.data.repositories

import com.reign.test.data.models.Article
import com.reign.test.data.services.ArticlesService
import retrofit2.Response

class HitsRemoteDataSource(
    private val characterService: ArticlesService
) {
    suspend fun getArticle(): Response<Article> {
        return characterService.getArticleByDateAsync()
    }
}
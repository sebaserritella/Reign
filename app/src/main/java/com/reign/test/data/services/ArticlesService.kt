package com.reign.test.data.services

import com.reign.test.data.models.Article
import retrofit2.Response
import retrofit2.http.GET

interface ArticlesService {

    @GET("search_by_date?query=android")
    suspend fun getArticleByDateAsync(): Response<Article>
}
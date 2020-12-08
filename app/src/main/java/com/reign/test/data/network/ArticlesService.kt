package com.reign.test.data.network

import com.reign.test.data.models.Article
import retrofit2.Call
import retrofit2.http.GET

interface ArticlesService {

    @GET("search_by_date?query=android")
    fun getArticleByDateAsync(): Call<Article>
}
package com.reign.test.di

import com.reign.test.data.models.Article
import com.reign.test.data.network.ArticleClient
import com.reign.test.data.network.ArticlesService
import com.reign.test.data.network.RequestInterceptor
import com.skydoves.sandwich.ResponseDataSource
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .build()
    }

    single {
        val baseUrl = "https://hn.algolia.com/api/v1/"
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(ArticlesService::class.java) }
    single { ArticleClient(get()) }
    single { ResponseDataSource<List<Article>>() }
}
package com.reign.test.di

import com.reign.test.data.services.ArticlesService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {

    fun provideArticleApi(): ArticlesService {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create()
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://hn.algolia.com/api/v1/")
            .build()


        return retrofit.create(ArticlesService::class.java)
    }


    single { provideArticleApi() }

}
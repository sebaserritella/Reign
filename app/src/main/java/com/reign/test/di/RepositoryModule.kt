package com.reign.test.di

import android.content.Context
import com.reign.test.data.repositories.ArticleRepository
import com.reign.test.data.repositories.HitsRemoteDataSource
import org.koin.dsl.module


val repositoryModule = module {

    fun provideHitRepository(
        api: HitsRemoteDataSource,
        context: Context
    ): ArticleRepository {
        return ArticleRepository(api,  context)
    }

    single { provideHitRepository(get(), get()) }
    single { HitsRemoteDataSource(get()) }

}
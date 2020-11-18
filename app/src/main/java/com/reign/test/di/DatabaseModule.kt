package com.reign.test.di

import android.app.Application
import androidx.room.Room
import com.reign.test.data.repositories.AppDatabase
import com.reign.test.data.ArticleDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()

    fun provideArticlesDao(database: AppDatabase): ArticleDao {
        return database.articleDao
    }


    single { provideDatabase(androidApplication()) }
    single { provideArticlesDao(get()) }
}
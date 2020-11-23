package com.reign.test.di

import org.koin.dsl.module

val databaseModule = module {

    /*
    fun provideDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, "database")
            //.fallbackToDestructiveMigration()
            .build()



    fun provideHitDao(database: AppDatabase): HitDao {
        return database.hitDao
    }


    single { provideDatabase(androidApplication()) }
    single { provideHitDao(get()) }

     */



}
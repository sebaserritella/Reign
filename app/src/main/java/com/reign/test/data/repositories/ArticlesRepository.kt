package com.reign.test.data.repositories

import android.content.Context
import com.reign.test.data.ArticleDao
import com.reign.test.data.models.Article
import com.reign.test.data.models.Hit
import com.reign.test.network.AppResult
import com.reign.test.network.handleApiError
import com.reign.test.network.handleSuccess
import com.reign.test.util.NetworkManager.isOnline
import com.reign.test.util.noNetworkConnectivityError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticlesRepository(
    private val remoteDataSource: ArticleRemoteDataSource,
    private val localDataSource: ArticleDao,
    private val context: Context,
) {

    fun deleteItem(hit: Hit) {
    }

    suspend fun getArticles(): AppResult<Article?> {
        if (isOnline(context)) {
            return try {
                val response = remoteDataSource.getArticles()
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.IO) { localDataSource.add(listOf(it)) }
                    }
                    handleSuccess(response)
                } else {
                    handleApiError(response)
                }
            } catch (e: Exception) {
                AppResult.Error(e)
            }
        } else {
            //check in db if the data exists
            val data = getArticleDataFromCache()
            return if (data.isNotEmpty()) {
                AppResult.Success(data.first())
            } else
            //no network
                context.noNetworkConnectivityError()
        }
    }

    private suspend fun getArticleDataFromCache(): List<Article> {
        return withContext(Dispatchers.IO) {
            localDataSource.findAll()
        }
    }
}
package com.reign.test.data.repositories

import androidx.lifecycle.MutableLiveData
import com.reign.test.data.models.Article
import com.reign.test.data.models.Hit
import com.reign.test.data.network.ArticleClient
import com.reign.test.data.persistance.ArticleDao
import com.skydoves.sandwich.*
import com.skydoves.sandwich.disposables.CompositeDisposable
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ArticlesRepository(
    private val articleClient: ArticleClient,
    private val articleDataSource: ResponseDataSource<Article>,
    private val articleDao: ArticleDao
) : Repository {
    init {
        Timber.d("Injection ArticlesRepository")
    }

    suspend fun loadArticles(
        force: Boolean,
        disposable: CompositeDisposable,
        error: (String) -> Unit
    ) = withContext(Dispatchers.IO) {
        val liveData = MutableLiveData<List<Hit>>()
        val articles = articleDao.getArticlesList()
        if (articles.isEmpty() || force) {
            articleClient.fetchArticles(articleDataSource, disposable) { apiResponse ->
                apiResponse
                    // handle the case when the API request gets a success response.
                    .onSuccess {
                        data.whatIfNotNull {
                            liveData.postValue(it.hits)
                            articleDao.insertArticleList(it)
                        }
                    }
                    // handle the case when the API request gets an error response.
                    // e.g. internal server error.
                    .onError { error(message()) }
                    // handle the case when the API request gets an exception response.
                    // e.g. network connection error.
                    .onException { error(message()) }
            }
        }

        liveData.apply {
            if (articles.isNotEmpty()) {
                postValue(articles[0].hits)
            }
        }
    }
}

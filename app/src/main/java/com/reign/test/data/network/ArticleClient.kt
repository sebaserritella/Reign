package com.reign.test.data.network

import com.reign.test.data.models.Article
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.DataRetainPolicy
import com.skydoves.sandwich.ResponseDataSource
import com.skydoves.sandwich.disposables.CompositeDisposable

class ArticleClient(private val articlesService: ArticlesService) {

    fun fetchArticles(
        dataSource: ResponseDataSource<Article>,
        disposable: CompositeDisposable,
        onResult: (response: ApiResponse<Article>) -> Unit
    ) {
        dataSource
            // Retain fetched data on the memory storage temporarily.
            // If request again, returns the retained data instead of re-fetching from the network.
            .dataRetainPolicy(DataRetainPolicy.RETAIN)
            // retry fetching data 3 times with 5000 milli-seconds time interval when the request gets failure.
            .retry(3, 5000L)
            // joins onto CompositeDisposable as a disposable and dispose onCleared().
            .joinDisposable(disposable)
            // combine network service to the data source.
            .combine(articlesService.getArticleByDateAsync(), onResult)
            // request API network call asynchronously.
            // if the request is successful, the data source will hold the success data.
            // in the next request after success, returns the cached API response with data.
            .request()
    }
}

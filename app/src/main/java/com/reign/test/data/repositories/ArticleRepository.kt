package com.reign.test.data.repositories

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.reign.test.App.Companion.database
import com.reign.test.data.models.Article
import com.reign.test.data.models.Hit
import com.reign.test.network.AppResult
import com.reign.test.network.handleApiError
import com.reign.test.util.NetworkManager
import com.reign.test.util.TAG
import com.reign.test.util.noNetworkConnectivityError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class ArticleRepository(
    private val remoteDataSource: HitsRemoteDataSource,
    private val context: Context,
) {
    suspend fun getHitsLocal(): AppResult<MutableList<Hit>?> {
        return withContext(Dispatchers.Default) {
            val aux = getHitsFromDatabase()
            AppResult.Success(aux)
        }
    }

    suspend fun getHits(): AppResult<MutableList<Hit>?> {
        deteleTable()
        if (NetworkManager.isOnline(context)) {
            return try {
                val response = remoteDataSource.getArticle()
                if (response.isSuccessful) {
                    response.body()?.let { article ->
                        Log.d(TAG, article.toString())

                        addNewHit(article)
                    }

                    withContext(Dispatchers.Default) {
                        val aux = getHitsFromDatabase()
                        AppResult.Success(aux)
                    }
                } else {
                    handleApiError(response)
                }
            } catch (e: Exception) {
                AppResult.Error(e)
            }
        } else {
            //check in db if the data exists
            val data = getHitsFromDatabase()
            return if (data != null) AppResult.Success(data)
            else
            //no network
                context.noNetworkConnectivityError()
        }
    }

    private fun deteleTable() {

    }

    private fun getArticlesCollection(): CollectionReference {
        return database.collection("articles")
    }

    private suspend fun addNewHit(article: Article) {
        return withContext(Dispatchers.IO) {
            getArticlesCollection().document("0").set(article)
                .addOnSuccessListener { documentReference ->
                }
                .addOnFailureListener { e ->
                }
        }
    }

    suspend fun updateHit(position: String, hit: Hit) {
        withContext(Dispatchers.IO) {
            hit.deleted = true
            val hit2 = hit.toMap()
            val documentReference = getArticlesCollection().document("0")

            val data = hashMapOf("articleId" to true)

            documentReference
                .set(data, SetOptions.merge())

            // return@withContext documentReference.update("hits", FieldValue.arrayUnion(hit2)).await()
        }

    }

    private suspend fun downloadSomething(): MutableList<Hit>? {
        val hits = withContext(Dispatchers.IO) {
            getArticlesCollection().document("0")
                .get()
                .addOnCompleteListener {
                    val documentSnapshot = it.result
                    if (documentSnapshot != null && documentSnapshot.exists()) {

                        //Log.d(TAG, documentSnapshot.data.toString())

                        val gson = Gson()
                        val jsonElement: JsonElement = gson.toJsonTree(documentSnapshot.data)
                        val article: Article = gson.fromJson(jsonElement, Article::class.java)

                        article.hits
                    }
                }.await()
        }

        val gson = Gson()
        val jsonElement: JsonElement = gson.toJsonTree(hits.data)
        val article: Article = gson.fromJson(jsonElement, Article::class.java)

        return article.hits
    }

    private suspend fun getHitsFromDatabase(): MutableList<Hit>? {
        val list = downloadSomething()
        val deleteds = list?.filter { x -> x.deleted == true }?.toMutableList()

        if (deleteds?.isNotEmpty() == true) {
            val aux = 5
        }

        return list?.filter { x -> x.deleted == false || x.deleted == null }?.toMutableList()
    }


    //private fun getHitsDataFromCache(): List<Hit>? {

    /*
   val postListener = object : ValueEventListener {
       override fun onDataChange(dataSnapshot: DataSnapshot) {

           val list = mutableListOf<Hit>()

           val oobject =
               dataSnapshot.getValue(Object::class.java) as HashMap<String, HashMap<String, Object>>?

           oobject?.let {
               for (value in it) {
                   val json = Gson().toJson(value.value)
                   val example = Gson().fromJson(json, Hit::class.java)
                   list.add(example)
               }
           }

           AppResult.Success(list)
       }

       override fun onCancelled(databaseError: DatabaseError) {
           // Getting Post failed, log a message
           Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
           // ...
       }
   }
   refDatabase.addListenerForSingleValueEvent(postListener)

     */
    //  }
}



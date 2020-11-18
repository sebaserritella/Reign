package com.reign.test.data.models

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type


object Converters {
    @TypeConverter
    fun fromArticle(article: Article): String {
        return JSONObject().apply {
            put("id", article.id)
            put("exhaustiveNbHits", article.exhaustiveNbHits)
            put("hits", article.hits)
            put("hitsPerPage", article.hitsPerPage)
            put("nbHits", article.nbHits)
            put("nbPages", article.nbPages)
            put("page", article.page)
            put("params", article.params)
            put("processingTimeMS", article.processingTimeMS)
            put("query", article.query)
        }.toString()
    }

    @TypeConverter
    fun toArticle(article: String): Article {
        val json = JSONObject(article)
        return Article(
            json.getInt("id"),
            json.getBoolean("exhaustiveNbHits"),
            fromHit("hits"),
            json.getInt("hitsPerPage"),
            json.getInt("nbHits"),
            json.getInt("nbPages"),
            json.getInt("page"),
            json.getString("params"),
            json.getInt("processingTimeMS"),
            json.getString("query"),
        )
    }

    @TypeConverter
    fun fromHit(value: String?): ArrayList<Hit> {
        val listType: Type = object : TypeToken<ArrayList<Hit?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayListHit(list: ArrayList<Hit?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
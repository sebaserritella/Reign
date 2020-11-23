package com.reign.test.data.models

import androidx.annotation.Keep
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import java.text.SimpleDateFormat
import java.util.*

@IgnoreExtraProperties
data class Article(
    val articleId: String = "0",
    val exhaustiveNbHits: Boolean?,
    val hits: MutableList<Hit>?,
    val hitsPerPage: Int?,
    val nbHits: Int?,
    val nbPages: Int?,
    val page: Int?,
    val params: String?,
    val processingTimeMS: Int?,
    val query: String?
) {

    @Keep
    fun Article() {
    }
}

@IgnoreExtraProperties
data class Hit(
    @get:PropertyName("highlightResult")
    val _highlightResult: HighlightResult?,
    @get:PropertyName("tags")
    val _tags: ArrayList<String>?,
    val author: String?,
    @get:PropertyName("comment_text_hit")
    val comment_text: String?,
    val created_at: String?,
    val created_at_i: Int?,
    val objectID: String?,
    val parent_id: Int?,
    val story_id: Int?,
    val story_title: String?,
    val story_url: String?,
    @field:JvmField
    var isDeleted: String?
) {
    @Keep
    fun Hit() {
    }

    @Exclude
    fun toMap(): HashMap<String, Any?> {
        return hashMapOf(
            "highlightResult" to _highlightResult,
            "tags" to _tags,
            "author" to author,
            "comment_text_hit" to comment_text,
            "created_at" to created_at,
            "created_at_i" to created_at_i,
            "objectID" to objectID,
            "parent_id" to parent_id,
            "story_id" to story_id,
            "story_title" to story_title,
            "story_url" to story_url,
            "deleted" to isDeleted
        )
    }

    fun markDeleted() {
        isDeleted = "true"
    }

    fun getCreatedTimeToShow(): String {
        try {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            format.timeZone = TimeZone.getTimeZone("Etc/UTC")
            val dateApi: Date = format.parse(created_at)

            val dateNow = Calendar.getInstance().time
            val timezone = TimeZone.getDefault()
            timezone.rawOffset

            val diff: Long = dateNow.time - dateApi.time
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24

            return when {
                minutes < 60 -> {
                    minutes.toString() + "m"
                }
                hours < 2 -> {
                    hours.toString() + "." + (minutes - 60).toInt() + "h"
                }
                hours < 24 -> {
                    hours.toString() + "h"
                }
                days == 1L -> {
                    "Yesterday"
                }
                else -> {
                    "$days days ago"
                }
            }
        } catch (e: Exception) {
            return created_at.toString()
        }
    }
}


data class HighlightResult(
    val author: Author?,
    @Exclude
    val comment_text: CommentText?,
    val story_title: StoryTitle?,
    val story_url: StoryUrl?,
    val title: Title?,
    val url: Url?
) {
    @Exclude
    fun toMap(): HashMap<String, Any?> {
        return hashMapOf(
            "author" to author,
            "comment_text" to comment_text,
            "story_title" to story_title,
            "story_url" to story_url,
            "title" to title,
            "url" to url

        )
    }
}

@IgnoreExtraProperties
data class Author(
    val fullyHighlighted: Boolean?,
    val matchLevel: String?,
    val matchedWords: List<String?>?,
    val value: String?
) {
    @Exclude
    fun toMap(): HashMap<String, Any?> {
        return hashMapOf(
            "fullyHighlighted" to fullyHighlighted,
            "matchLevel" to matchLevel,
            "matchedWords" to matchedWords,
            "value" to value
        )
    }
}

@IgnoreExtraProperties
data class CommentText(
    val fullyHighlighted: Boolean?,
    val matchLevel: String?,
    val matchedWords: List<String?>?,
    val value: String?
) {
    @Exclude
    fun toMap(): HashMap<String, Any?> {
        return hashMapOf(
            "fullyHighlighted" to fullyHighlighted,
            "matchLevel" to matchLevel,
            "matchedWords" to matchedWords,
            "value" to value
        )
    }
}

@IgnoreExtraProperties
data class StoryTitle(
    val matchLevel: String?,
    val matchedWords: List<Any?>?,
    val value: String?
) {
    @Exclude
    fun toMap(): HashMap<String, Any?> {
        return hashMapOf(
            "matchLevel" to matchLevel,
            "matchedWords" to matchedWords,
            "value" to value
        )
    }
}

@IgnoreExtraProperties
data class StoryUrl(
    val matchLevel: String?,
    val matchedWords: List<Any?>?,
    val value: String?
) {
    @Exclude
    fun toMap(): HashMap<String, Any?> {
        return hashMapOf(
            "matchLevel" to matchLevel,
            "matchedWords" to matchedWords,
            "value" to value
        )
    }
}

@IgnoreExtraProperties
data class Title(
    val fullyHighlighted: Boolean?,
    val matchLevel: String?,
    val matchedWords: List<String?>?,
    val value: String?
) {
    @Exclude
    fun toMap(): HashMap<String, Any?> {
        return hashMapOf(
            "fullyHighlighted" to fullyHighlighted,
            "matchLevel" to matchLevel,
            "matchedWords" to matchedWords,
            "value" to value
        )
    }
}

@IgnoreExtraProperties
data class Url(
    val fullyHighlighted: Boolean?,
    val matchLevel: String?,
    val matchedWords: List<String?>?,
    val value: String?
) {
    @Exclude
    fun toMap(): HashMap<String, Any?> {
        return hashMapOf(
            "fullyHighlighted" to fullyHighlighted,
            "matchLevel" to matchLevel,
            "matchedWords" to matchedWords,
            "value" to value
        )
    }
}

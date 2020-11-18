package com.reign.test.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val exhaustiveNbHits: Boolean?,
    @Embedded
    val hits: ArrayList<Hit>,
    val hitsPerPage: Int?,
    val nbHits: Int?,
    val nbPages: Int?,
    val page: Int?,
    val params: String?,
    val processingTimeMS: Int?,
    val query: String?
)

data class Hit(
    val _highlightResult: HighlightResult?,
    val _tags: List<String?>?,
    val author: String?,
    val comment_text: String?,
    val created_at: String?,
    val created_at_i: Int?,
    val num_comments: Any?,
    val objectID: String?,
    val parent_id: Int?,
    val points: Any?,
    val story_id: Int?,
    val story_text: Any?,
    val story_title: String?,
    val story_url: String?,
    val title: Any?,
    val url: Any?
) {
    fun getCreatedTimeToShow(): String {
        try {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val dateApi: Date = format.parse(created_at)

            val dateNow = Calendar.getInstance().time

            val diff: Long = dateApi.time - dateNow.time
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
    @Embedded
    val author: Author?,
    @Embedded
    val comment_text: CommentText?,
    @Embedded
    val story_title: StoryTitle?,
    @Embedded
    val story_url: StoryUrl?,
    @Embedded
    val title: Title?,
    @Embedded
    val url: Url?
)

data class Author(
    val fullyHighlighted: Boolean?,
    val matchLevel: String?,
    val matchedWords: List<String?>?,
    val value: String?
)

data class CommentText(
    val fullyHighlighted: Boolean?,
    val matchLevel: String?,
    val matchedWords: List<String?>?,
    val value: String?
)

data class StoryTitle(
    val matchLevel: String?,
    val matchedWords: List<Any?>?,
    val value: String?
)

data class StoryUrl(
    val matchLevel: String?,
    val matchedWords: List<Any?>?,
    val value: String?
)

data class Title(
    val fullyHighlighted: Boolean?,
    val matchLevel: String?,
    val matchedWords: List<String?>?,
    val value: String?
)

data class Url(
    val fullyHighlighted: Boolean?,
    val matchLevel: String?,
    val matchedWords: List<String?>?,
    val value: String?
)

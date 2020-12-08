package com.reign.test.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Entity
@Parcelize
data class Article(
    @PrimaryKey
    val id: Long = 0L,
    val exhaustiveNbHits: Boolean,
    val hits: List<Hit>,
    val hitsPerPage: Int,
    val nbHits: Int,
    val nbPages: Int,
    val page: Int,
    val params: String,
    val processingTimeMS: Int,
    val query: String
) : Parcelable


@Parcelize
data class Hit(
    val id: Long,
    val _highlightResult: HighlightResult,
    val _tags: List<String>,
    val author: String,
    val comment_text: String,
    val created_at: String,
    val created_at_i: Int,
    val num_comments: String,
    val objectID: String,
    val parent_id: Int,
    val points: String,
    val story_id: Int,
    val story_text: String,
    val story_title: String,
    val story_url: String,
    val title: String,
    val url: String,
    var deleted: Boolean = false
) : Parcelable {
    fun markDeleted() {
        deleted = true
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

@Parcelize
data class HighlightResult(
    val id: Long,
    val author: Author,
    val comment_text: CommentText,
    val story_title: StoryTitle,
    val story_url: StoryUrl,
    val title: Title,
    val url: Url
) : Parcelable

@Parcelize
data class Author(
    val id: Long,
    val fullyHighlighted: Boolean,
    val matchLevel: String,
    val matchedWords: List<String>,
    val value: String
) : Parcelable

@Parcelize
data class CommentText(
    val id: Long,
    val fullyHighlighted: Boolean,
    val matchLevel: String,
    val matchedWords: List<String>,
    val value: String
) : Parcelable

@Parcelize
data class StoryTitle(
    val id: Long,
    val matchLevel: String,
    val matchedWords: List<String>,
    val value: String
) : Parcelable

@Parcelize
data class StoryUrl(
    val id: Long,
    val matchLevel: String,
    val matchedWords: List<String>,
    val value: String
) : Parcelable

@Parcelize
data class Title(
    val id: Long,
    val fullyHighlighted: Boolean,
    val matchLevel: String,
    val matchedWords: List<String>,
    val value: String
) : Parcelable

@Parcelize
data class Url(
    val id: Long,
    val fullyHighlighted: Boolean,
    val matchLevel: String,
    val matchedWords: List<String>,
    val value: String
) : Parcelable

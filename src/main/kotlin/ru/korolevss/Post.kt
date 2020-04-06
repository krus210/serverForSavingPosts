package ru.korolevss

import java.time.ZonedDateTime

data class PostModel(
    val id: Long? = null,
    val textOfPost: String? = null,
    val dateOfPost: ZonedDateTime? = null,
    val nameAuthor: String,
    val photoAuthor: Int? = null,
    var sharesCount: Int = 0,
    var commentsCount: Int = 0,
    var likesCount: Int = 0,
    var isLikedByUser: Boolean = false,
    var isCommentedByUser: Boolean = false,
    var isSharedByUser: Boolean = false,
    val postType: PostType = PostType.POST,
    val source: PostModel? = null,
    val address: String? = null,
    val coordinates: Coordinates? = null,
    val sourceVideo: String? = null,
    val sourceAd: String? = null
)
enum class PostType {
    POST, EVENT, REPOST, YOUTUBE, AD_POST
}

data class Coordinates(
    val longitude: String,
    val latitude: String
)
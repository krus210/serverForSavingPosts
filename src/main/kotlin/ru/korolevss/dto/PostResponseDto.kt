package ru.korolevss.dto

import ru.korolevss.Coordinates
import ru.korolevss.PostModel
import ru.korolevss.PostType
import java.time.ZonedDateTime

class PostResponseDto(
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
) {
    companion object {
        fun fromModel(postModel: PostModel) = PostResponseDto(
            id = postModel.id,
            textOfPost = postModel.textOfPost,
            dateOfPost = postModel.dateOfPost,
            nameAuthor = postModel.nameAuthor,
            photoAuthor = postModel.photoAuthor,
            sharesCount = postModel.sharesCount,
            commentsCount = postModel.commentsCount,
            likesCount = postModel.likesCount,
            isLikedByUser = postModel.isLikedByUser,
            isCommentedByUser = postModel.isCommentedByUser,
            isSharedByUser = postModel.isSharedByUser,
            postType = postModel.postType,
            source = postModel.source,
            address = postModel.address,
            coordinates = postModel.coordinates,
            sourceVideo = postModel.sourceVideo,
            sourceAd = postModel.sourceAd
        )
    }
}
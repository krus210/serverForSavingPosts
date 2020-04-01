package ru.korolevss.dto

import ru.korolevss.PostModel
import ru.korolevss.PostType

class PostRequestDto (
    val id: Long,
    val textOfPost: String? = null,
    val nameAuthor: String,
    val photoAuthor: Int? = null,
    val postType: PostType = PostType.POST,
    val source: PostModel? = null,
    val address: String? = null,
    val coordinates: Pair<String, String>? = null,
    val sourceVideo: String? = null,
    val sourceAd: String? = null
) {
    companion object {
        fun fromModel(postModel: PostModel) = PostRequestDto(
            id = postModel.id,
            textOfPost = postModel.textOfPost,
            nameAuthor = postModel.nameAuthor,
            photoAuthor = postModel.photoAuthor,
            postType = postModel.postType,
            source = postModel.source,
            address = postModel.address,
            coordinates = postModel.coordinates,
            sourceVideo = postModel.sourceVideo,
            sourceAd = postModel.sourceAd
        )
    }
}
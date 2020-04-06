package ru.korolevss.dto

import ru.korolevss.Coordinates
import ru.korolevss.PostModel
import ru.korolevss.PostType

class PostRequestDto (
    val textOfPost: String? = null,
    val nameAuthor: String,
    val photoAuthor: Int? = null,
    val postType: PostType = PostType.POST,
    val source: PostModel? = null,
    val address: String? = null,
    val coordinates: Coordinates? = null,
    val sourceVideo: String? = null,
    val sourceAd: String? = null
)
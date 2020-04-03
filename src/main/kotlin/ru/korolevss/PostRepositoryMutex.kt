package ru.korolevss

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.LocalDateTime

class PostRepositoryMutex: PostRepository {

    private var nextId = 1L
    private val items = mutableListOf<PostModel>()
    private val mutex = Mutex()

    override suspend fun getAll(): List<PostModel> =
        mutex.withLock {
            items.reversed()
        }


    override suspend fun getById(id: Long): PostModel? =
        mutex.withLock {
            items.find { it.id == id }
        }

    override suspend fun save(item: PostModel): PostModel =
        mutex.withLock {
            when (val index = items.indexOfFirst { it.id == item.id }) {
                -1 -> {
                    val date =LocalDateTime.now().toString()
                    val copy = item.copy(id = nextId++, dateOfPost = date)
                    items.add(copy)
                    copy
                }
                else -> {
                    items[index] = item
                    item
                }
            }
        }

    override suspend fun removeById(id: Long) {
        mutex.withLock {
            items.removeIf { it.id == id }
        }
    }

    override suspend fun likeById(id: Long): PostModel? =
        mutex.withLock {
            val index = items.indexOfFirst { it.id == id }
            if (index < 0) {
                return@withLock null
            }

            val post = items[index]
            val newPost = post.copy(likesCount= post.likesCount + 1, isLikedByUser = true)
            items[index] = newPost

            newPost
        }

    override suspend fun dislikeById(id: Long): PostModel? =
        mutex.withLock {
            val index = items.indexOfFirst { it.id == id }
            if (index < 0) {
                return@withLock null
            }

            val post = items[index]

            val newPost = post.copy(likesCount= post.likesCount - 1, isLikedByUser = false)

            items[index] = newPost

            newPost
        }

    override suspend fun commentById(id: Long): PostModel? =
        mutex.withLock {
            val index = items.indexOfFirst { it.id == id }
            if (index < 0) {
                return@withLock null
            }

            val post = items[index]

            val newPost = post.copy(commentsCount= post.commentsCount + 1, isCommentedByUser = true)

            items[index] = newPost

            newPost
        }

    override suspend fun shareById(id: Long): PostModel? =
        mutex.withLock {
            val index = items.indexOfFirst { it.id == id }
            if (index < 0) {
                return@withLock null
            }

            val post = items[index]

            val newPost = post.copy(sharesCount= post.sharesCount + 1, isSharedByUser = true)

            items[index] = newPost

            newPost
        }
}
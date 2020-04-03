package ru.korolevss

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.server.cio.EngineMain
import io.ktor.util.KtorExperimentalAPI
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.kodein.di.ktor.KodeinFeature
import org.kodein.di.ktor.kodein
import ru.korolevss.dto.PostRequestDto
import ru.korolevss.dto.PostResponseDto

fun main(args: Array<String>) {
    EngineMain.main(args)
}

@KtorExperimentalAPI
fun Application.module() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
        }
    }

    install(KodeinFeature) {
        bind<PostRepository>() with singleton {
            PostRepositoryMutex()
        }
    }

    install(StatusPages) {
        exception<NotImplementedError> { e ->
            call.respond(HttpStatusCode.NotImplemented, Error("Error"))
            throw e
        }
        exception<ParameterConversionException> { e ->
            call.respond(HttpStatusCode.BadRequest)
            throw e
        }
        exception<Throwable> { e ->
            call.respond(HttpStatusCode.InternalServerError)
            throw e
        }
        exception<NotFoundException> { e ->
            call.respond(HttpStatusCode.NotFound)
            throw e
        }
    }


    install(Routing) {
        val repo by kodein().instance<PostRepository>()

        route("/api/v1/posts") {
            get {
                val response = repo.getAll().map(PostResponseDto.Companion::fromModel)
                call.respond(response)
            }
            get("/{id}") {
                val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException("id", "Long")
                val model = repo.getById(id) ?: throw NotFoundException()
                val response = PostResponseDto.fromModel(model)
                call.respond(response)
            }
            get("/{id}/like") {
                val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException("id", "Long")
                val model = repo.likeById(id) ?: throw NotFoundException()
                val response = PostResponseDto.fromModel(model)
                call.respond(response)
            }
            get("/{id}/dislike") {
                val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException("id", "Long")
                val model = repo.dislikeById(id) ?: throw NotFoundException()
                val response = PostResponseDto.fromModel(model)
                call.respond(response)
            }
            get("/{id}/comment") {
                val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException("id", "Long")
                val model = repo.commentById(id) ?: throw NotFoundException()
                val response = PostResponseDto.fromModel(model)
                call.respond(response)
            }
            get("/{id}/share") {
                val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException("id", "Long")
                val model = repo.shareById(id) ?: throw NotFoundException()
                val response = PostResponseDto.fromModel(model)
                call.respond(response)
            }
            post {
                val request = call.receive<PostRequestDto>()
                val model = PostModel(id = request.id, textOfPost = request.textOfPost,
                    nameAuthor = request.nameAuthor, photoAuthor = request.photoAuthor,
                    postType = request.postType, source = request.source, address = request.address,
                coordinates = request.coordinates, sourceVideo = request.sourceVideo, sourceAd = request.sourceAd)
                val response = repo.save(model)
                call.respond(response)
            }
            delete("/{id}") {
                val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException("id", "Long")
                repo.removeById(id)
            }
        }
    }
}




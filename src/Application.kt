package io.kraftsman.ktor.api

import io.kraftsman.ktor.api.responds.TaskRespond
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        jackson {

        }
    }

    routing {
        get("/") {
            val task = TaskRespond(
                "Task 1",
                false,
                "2019-11-16 00:00:00",
                "2019-11-16 00:00:00"
            )

            call.respond(task)
        }
    }

}

package io.kraftsman.ktor.api

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    routing {
        get("/") {
            //language=JSON
            call.respondText("{\n  \"tasks\": [\n    {\n      \"title\": \"Task 1\",\n      \"completed\": false\n    },\n    {\n      \"title\": \"Task 2\",\n      \"completed\": true\n    }\n  ]\n}", ContentType.Application.Json, HttpStatusCode.OK)
        }
    }

}

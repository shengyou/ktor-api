package io.kraftsman.ktor.api

import com.github.javafaker.Faker
import io.kraftsman.ktor.api.responds.TaskRespond
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import org.joda.time.DateTime
import kotlin.random.Random

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
            val tasks = mutableListOf<TaskRespond>()
            val faker = Faker()
            for (i in 1..10) {
                tasks.add(
                    TaskRespond(
                        title = "Buy ${faker.beer().name()}",
                        completed = listOf(true, false).shuffled().first(),
                        createdAt = DateTime.now().plusDays(i).toString("yyyy-MM-dd HH:mm:ss"),
                        updatedAt = DateTime.now().plusHours(Random.nextInt(1, 10)).toString("yyyy-MM-dd HH:mm:ss")
                    )
                )
            }

            call.respond(mapOf("tasks" to tasks))
        }
    }

}

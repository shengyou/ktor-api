package io.kraftsman.ktor.api

import com.github.javafaker.Faker
import io.kraftsman.ktor.api.entities.Task
import io.kraftsman.ktor.api.requests.TaskRequest
import io.kraftsman.ktor.api.responds.TaskRespond
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
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

    Database.connect(
        url = "jdbc:mysql://127.0.0.1:8889/ktor_api?useUnicode=true&characterEncoding=utf-8&useSSL=false",
        driver = "com.mysql.jdbc.Driver",
        user = "root",
        password = "root"
    )

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

        get("/api/v1/tasks") {
            val tasks = transaction {
                Task.all().sortedByDescending { it.id }.map {
                    TaskRespond(
                        title = it.title,
                        completed = it.completed,
                        createdAt = it.createdAt.toString("yyyy-MM-dd HH:mm:ss"),
                        updatedAt = it.updatedAt.toString("yyyy-MM-dd HH:mm:ss")
                    )
                }
            }

            call.respond(mapOf("tasks" to tasks))
        }

        post("/api/v1/tasks") {
            val request = call.receive<TaskRequest>()
            transaction {
                Task.new {
                    title = request.title
                    completed = false
                    createdAt = DateTime.now()
                    updatedAt = DateTime.now()
                }
            }

            call.respond(HttpStatusCode.Created)
        }
    }
}

package io.kraftsman.ktor.api

import com.github.javafaker.Faker
import io.kraftsman.ktor.api.entities.Task
import io.kraftsman.ktor.api.tables.Tasks
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import kotlin.random.Random

fun main() {
    Database.connect(
        url = "jdbc:mysql://127.0.0.1:8889/ktor_api?useUnicode=true&characterEncoding=utf-8&useSSL=false",
        driver = "com.mysql.jdbc.Driver",
        user = "root",
        password = "root"
    )

    transaction {
        SchemaUtils.drop(Tasks)
        SchemaUtils.create(Tasks)
    }

    transaction {
        val faker = Faker()
        for (i in 1..10) {
            Task.new {
                title = "Buy ${faker.beer().name()}"
                completed = listOf(true, false).shuffled().first()
                createdAt = DateTime.now().plusDays(i)
                updatedAt = DateTime.now().plusDays(i).plusHours(Random.nextInt(1, 10))
            }
        }
    }
}

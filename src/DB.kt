package io.kraftsman.ktor.api

import io.kraftsman.ktor.api.tables.Tasks
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    Database.connect(
        url = "jdbc:mysql://127.0.0.1:8889/ktor_website?useUnicode=true&characterEncoding=utf-8&useSSL=false",
        driver = "com.mysql.jdbc.Driver",
        user = "root",
        password = "root"
    )

    transaction {
        SchemaUtils.drop(Tasks)
        SchemaUtils.create(Tasks)
    }
}

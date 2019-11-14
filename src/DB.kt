package io.kraftsman.ktor.api

import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        url = "jdbc:mysql://127.0.0.1:8889/ktor_website?useUnicode=true&characterEncoding=utf-8&useSSL=false",
        driver = "com.mysql.jdbc.Driver",
        user = "root",
        password = "root"
    )
}

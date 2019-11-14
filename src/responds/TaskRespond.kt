package io.kraftsman.ktor.api.responds

data class TaskRespond(
    val title: String,
    val completed: Boolean,
    val createdAt: String,
    val updatedAt: String
)

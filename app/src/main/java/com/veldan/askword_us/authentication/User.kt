package com.veldan.askword_us.authentication

data class User(
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
)
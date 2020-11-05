package com.veldan.askword_us.authentication

data class User(
    val name: String = "",
    val surname: String = "",
    var email: String = "",
    val password: String = "",
)
package com.mandiri.goldmarket.data.remote.response.auth

data class LoginResponse(
    val address: String,
    val dateOfBirth: String,
    val email: String,
    val firstName: String,
    val id: String,
    val lastName: String,
    val roles: List<String>,
    val status: Int,
    val token: String,
    val type: String,
    val userName: String
)

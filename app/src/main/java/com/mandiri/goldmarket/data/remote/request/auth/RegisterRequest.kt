package com.mandiri.goldmarket.data.remote.request.auth

data class RegisterRequest(
    val address: String,
    val dateOfBirth: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: List<String>,
    val status: Int,
    val userName: String,
    val userPassword: String
)
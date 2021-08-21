package com.mandiri.goldmarket.data.remote.response.customer

data class CustomerResponse(
    val address: String,
    val dateOfBirth: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val photoProfile: String,
    val status: Int,
    val userName: String,
    val userPassword: Any
)
package com.mandiri.goldmarket.data.remote.request.customer

data class CustomerRequest(
    val address: String,
    val dateOfBirth: String,
    val email: String,
    val firstName: String,
    val id: String,
    val lastName: String,
    val photoProfile: String,
    val status: Int,
    val userName: String,
    val userPassword: Any
)
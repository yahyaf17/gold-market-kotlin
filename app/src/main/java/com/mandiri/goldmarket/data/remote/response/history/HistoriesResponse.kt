package com.mandiri.goldmarket.data.remote.response.history

data class HistoriesResponse(
    val content: List<Content>,
    val number: Int,
    val numberOfElements: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)
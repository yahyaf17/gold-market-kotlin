package com.mandiri.goldmarket.data.remote.response.product

data class ProductResponse(
    val historyPrices: List<Any>,
    val id: Int,
    val productDesc: String,
    val productImage: String,
    val productName: String,
    val productPriceBuy: Int,
    val productPriceSell: Int,
    val productStatus: Int
)
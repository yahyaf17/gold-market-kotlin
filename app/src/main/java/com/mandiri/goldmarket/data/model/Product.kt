package com.mandiri.goldmarket.data.model

import java.math.BigDecimal

data class Product(val id: String, var name: String, var priceBuy: BigDecimal, var priceSell: BigDecimal)

package com.mandiri.goldmarket.data.model

import java.math.BigDecimal

data class Pocket(
    var name: String,
    var product: String,
    var amount: Double,
    var totalPrice: BigDecimal
    )

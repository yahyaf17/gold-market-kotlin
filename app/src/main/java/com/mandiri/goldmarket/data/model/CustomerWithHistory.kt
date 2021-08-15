package com.mandiri.goldmarket.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class CustomerWithHistory (
    @Embedded val customer: Customer,
    @Relation(
        parentColumn = "customer_id",
        entityColumn = "customer_owner_id"
    )
    val history: List<History>
)
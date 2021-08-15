package com.mandiri.goldmarket.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class CustomerWithPockets(
    @Embedded val customer: Customer,
    @Relation(
        parentColumn = "customer_id",
        entityColumn = "customer_owner_id"
    )
    val pocket: List<Pocket>
)

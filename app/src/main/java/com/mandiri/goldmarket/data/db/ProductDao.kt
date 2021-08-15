package com.mandiri.goldmarket.data.db

import androidx.room.Dao
import androidx.room.Query
import com.mandiri.goldmarket.data.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product where product_id=:id")
    suspend fun getProductById(id: Int): Product

}
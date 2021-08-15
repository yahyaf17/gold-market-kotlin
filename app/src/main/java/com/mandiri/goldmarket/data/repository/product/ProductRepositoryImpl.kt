package com.mandiri.goldmarket.data.repository.product

import com.mandiri.goldmarket.data.model.Product
import java.math.BigDecimal

class ProductRepositoryImpl: ProductRepository {

    override fun findProductById(id: Int): Product? {
        return productDB.find { product ->
            product.productId == id
        }
    }

    companion object {
        var productDB = mutableListOf(
            Product(
                1,
                 "Gold",
                872000.0,
                890000.0,
            ),
        )
    }
}
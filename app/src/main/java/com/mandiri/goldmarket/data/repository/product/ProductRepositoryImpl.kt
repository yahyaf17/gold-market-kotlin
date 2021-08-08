package com.mandiri.goldmarket.data.repository.product

import com.mandiri.goldmarket.data.model.Product
import java.math.BigDecimal

class ProductRepositoryImpl: ProductRepository {

    override fun findProductById(id: String): Product? {
        return productDB.find { product ->
            product.id == id
        }
    }

    companion object {
        var productDB = mutableListOf(
            Product(
                "1",
                "Gold",
                BigDecimal(872000),
                BigDecimal(890000),
            ),
        )
    }
}
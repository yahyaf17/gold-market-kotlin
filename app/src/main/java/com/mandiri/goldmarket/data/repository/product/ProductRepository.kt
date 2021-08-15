package com.mandiri.goldmarket.data.repository.product

import com.mandiri.goldmarket.data.model.Product

interface ProductRepository {
    fun findProductById(id: Int): Product?
}
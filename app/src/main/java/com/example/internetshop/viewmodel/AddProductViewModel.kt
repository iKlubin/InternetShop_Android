package com.example.internetshop.viewmodel

import androidx.lifecycle.ViewModel
import com.example.internetshop.data.model.Product
import com.example.internetshop.data.repository.ProductRepository

class AddProductViewModel : ViewModel() {
    private val productRepository = ProductRepository()

    suspend fun addProduct(
        name: String,
        description: String,
        extendedDescription: String,
        price: Double,
        category: String,
        tags: String,
        imageUrl: String
    ): Boolean {
        return try {
            val product = Product(
                Name = name,
                Description = description,
                ExtendedDescription = extendedDescription,
                Price = price,
                Category = category,
                Tags = tags,
                ImageUrl = imageUrl
            )
            productRepository.addProduct(product)
            true
        } catch (e: Exception) {
            false
        }
    }
}

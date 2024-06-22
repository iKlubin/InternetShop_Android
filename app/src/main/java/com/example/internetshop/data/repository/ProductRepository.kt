package com.example.internetshop.data.repository

import com.example.internetshop.data.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()
    private val productsCollection = db.collection("products")

    suspend fun getProducts(): List<Product> {
        return try {
            val snapshot = productsCollection.get().await()
            snapshot.documents.mapNotNull { it.toObject(Product::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addProduct(product: Product): Boolean {
        return try {
            productsCollection.document(product.id).set(product).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getProductsByCategory(categoryId: String): List<Product> {
        return try {
            val snapshot = productsCollection.whereEqualTo("categoryId", categoryId).get().await()
            snapshot.documents.mapNotNull { it.toObject(Product::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getProductById(productId: String): Product? {
        return try {
            val snapshot = productsCollection.document(productId).get().await()
            snapshot.toObject(Product::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateProduct(product: Product): Boolean {
        return try {
            productsCollection.document(product.id).set(product).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteProduct(productId: String): Boolean {
        return try {
            productsCollection.document(productId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}

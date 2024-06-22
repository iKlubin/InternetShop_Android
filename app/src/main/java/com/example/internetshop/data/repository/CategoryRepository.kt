package com.example.internetshop.data.repository

import com.example.internetshop.data.model.Category
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CategoryRepository {

    private val db = FirebaseFirestore.getInstance()
    private val categoriesCollection = db.collection("categories")

    suspend fun getCategories(): List<Category> {
        return try {
            val snapshot = categoriesCollection.get().await()
            snapshot.documents.mapNotNull { it.toObject(Category::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}

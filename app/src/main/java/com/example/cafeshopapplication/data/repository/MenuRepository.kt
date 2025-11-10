package com.example.cafeshopapplication.data.repository

import com.example.cafeshopapplication.data.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MenuRepository {
    private val db = FirebaseFirestore.getInstance()

    private val productCollection = db.collection("products")
    suspend fun getAllProducts(): List<Product> {
        return try {
            val snapshot = productCollection.get().await()

            snapshot.toObjects(Product::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
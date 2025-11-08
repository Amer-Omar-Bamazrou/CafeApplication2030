package com.example.cafeshopapplication.data.repository

import com.example.cafeshopapplication.data.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MenuRepository {

    // Getting Instances of firestore database
    private val db = FirebaseFirestore.getInstance()

    private val productCollection = db.collection("products")

    // Function to get all products from the database
    suspend fun getAllProducts(): List<Product> {
        return try {
            // Ask Firestore for all documents in the 'products' collection
            val snapshot = productCollection.get().await()

            // Convert the documents we get back into our 'Product' data class
            snapshot.toObjects(Product::class.java)
        } catch (e: Exception) {
            // If it fails, return an empty list
            emptyList()
        }
    }
}
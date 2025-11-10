package com.example.cafeshopapplication.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cafeshopapplication.data.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProductDetailsViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // This will hold the single product we fetch
    private val _product = MutableLiveData<Product?>()
    val product: LiveData<Product?> = _product

    /**
     * This function takes a product ID and fetches that
     * specific document from the 'products' collection.
     */
    fun fetchProductDetails(productId: String) {
        viewModelScope.launch {
            try {
                // Get the specific document from Firestore
                val document = db.collection("products").document(productId).get().await()

                // Convert that document into our Product data class
                val product = document.toObject(Product::class.java)
                _product.postValue(product)

            } catch (e: Exception) {
                // If it fails (e.g., bad ID), post null
                _product.postValue(null)
            }
        }
    }
}
package com.example.cafeshopapplication.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cafeshopapplication.data.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MenuViewModel : ViewModel() {

    // 1. Get an instance of Firestore directly
    private val db = FirebaseFirestore.getInstance()

    // 2. Private LiveData to hold the product list
    private val _products = MutableLiveData<List<Product>>()

    // 3. Public LiveData for the Activity to observe
    val products: LiveData<List<Product>> = _products

    // 4. Private LiveData for loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    /**
     * This 'init' block runs automatically when the ViewModel is first created.
     */
    init {
        fetchProducts()
    }

    /**
     * This function now does the Firestore query directly.
     */
    private fun fetchProducts() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                // 5. Ask Firestore for all documents in the 'products' collection
                val snapshot = db.collection("products").get().await()

                // 6. Convert the documents into our 'Product' data class
                val productList = snapshot.toObjects(Product::class.java)

                _products.postValue(productList)

            } catch (e: Exception) {
                // If it fails, post an empty list
                _products.postValue(emptyList())
            }

            _isLoading.postValue(false)
        }
    }
}
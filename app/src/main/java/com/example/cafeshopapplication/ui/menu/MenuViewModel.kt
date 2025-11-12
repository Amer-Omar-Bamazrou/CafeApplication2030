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
    private val db = FirebaseFirestore.getInstance()
    private val _products = MutableLiveData<List<Product>>()

    val products: LiveData<List<Product>> = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchProducts()
    }


    fun fetchProducts() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val snapshot = db.collection("products").get().await()
                val productList = snapshot.toObjects(Product::class.java)
                _products.postValue(productList)
            } catch (e: Exception) {
                _products.postValue(emptyList())
            }
            _isLoading.postValue(false)
        }
    }
}
package com.example.cafeshopapplication.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cafeshopapplication.ui.cart.CartViewModel
import com.example.cafeshopapplication.data.model.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OrderViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val cartViewModel = CartViewModel()

    // --- CHANGE 1: This will hold the new Order ID (String) or null ---
    private val _orderStatus = MutableLiveData<String?>()
    val orderStatus: LiveData<String?> = _orderStatus

    fun placeOrder() {
        viewModelScope.launch {
            val currentUser = auth.currentUser
            val cartItems = cartViewModel.cartItems.value.orEmpty()
            val totalPrice = cartViewModel.totalPrice.value ?: 0.0

            if (currentUser == null || cartItems.isEmpty()) {
                _orderStatus.postValue(null) // Post failure
                return@launch
            }

            val order = Order(
                cusId = currentUser.uid,
                totalPrice = totalPrice,
                items = cartItems
            )

            try {
                // --- CHANGE 2: Get the new order's document ---
                val newOrderDocument = db.collection("orders").add(order).await()

                cartViewModel.clearCart()

                // --- CHANGE 3: Post the NEW Order ID as the success value ---
                _orderStatus.postValue(newOrderDocument.id)

            } catch (e: Exception) {
                _orderStatus.postValue(null) // Post failure
            }
        }
    }
}
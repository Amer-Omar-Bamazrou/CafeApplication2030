package com.example.cafeshopapplication.ui.cart

import androidx.lifecycle.ViewModel
import com.example.cafeshopapplication.data.model.CartItem // <-- ADD THIS IMPORT
import com.example.cafeshopapplication.data.model.Product
import com.example.cafeshopapplication.data.repository.CartRepository

class CartViewModel : ViewModel() {

    val cartItems = CartRepository.cartItems
    val totalPrice = CartRepository.totalPrice

    fun addToCart(product: Product) {
        CartRepository.addToCart(product)
    }

    // --- ADD THIS NEW FUNCTION ---
    fun removeItemFromCart(cartItem: CartItem) {
        CartRepository.removeItemFromCart(cartItem)
    }

    // --- ADD THIS NEW FUNCTION ---
    fun updateItemQuantity(cartItem: CartItem, newQuantity: Int) {
        CartRepository.updateItemQuantity(cartItem, newQuantity)
    }

    fun clearCart(){
        CartRepository.clearCart()

    }
}
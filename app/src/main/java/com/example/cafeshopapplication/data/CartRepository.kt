package com.example.cafeshopapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cafeshopapplication.data.model.CartItem
import com.example.cafeshopapplication.data.model.Product

object CartRepository {

    private val _cartItems = MutableLiveData<List<CartItem>>(emptyList())
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _totalPrice = MutableLiveData<Double>(0.0)
    val totalPrice: LiveData<Double> = _totalPrice


    fun addToCart(product: Product) {
        val currentList = _cartItems.value.orEmpty().toMutableList()

        // --- ADD SAFE CALL HERE ---
        val existingItem = currentList.find { it.product?.idProd == product.idProd }

        if (existingItem != null) {
            existingItem.quantity++
        } else {
            currentList.add(CartItem(product = product, quantity = 1))
        }

        _cartItems.value = currentList
        calculateTotalPrice()
    }

    fun removeItemFromCart(cartItem: CartItem) {
        val currentList = _cartItems.value.orEmpty().toMutableList()
        currentList.remove(cartItem)
        _cartItems.value = currentList
        calculateTotalPrice()
    }

    fun updateItemQuantity(cartItem: CartItem, newQuantity: Int) {
        val currentList = _cartItems.value.orEmpty().toMutableList()

        // --- ADD SAFE CALL HERE ---
        val itemToUpdate = currentList.find { it.product?.idProd == cartItem.product?.idProd }

        if (itemToUpdate != null) {
            if (newQuantity > 0) {
                itemToUpdate.quantity = newQuantity
            } else {
                currentList.remove(itemToUpdate)
            }
        }

        _cartItems.value = currentList
        calculateTotalPrice()
    }

    private fun calculateTotalPrice() {
        val list = _cartItems.value.orEmpty()
        var total = 0.0
        for (item in list) {
            // --- ADD SAFE CALL HERE ---
            // If product is not null, use its price. If it is null, use 0.0.
            val itemPrice = item.product?.priceProd ?: 0.0
            total += itemPrice * item.quantity
        }
        _totalPrice.value = total
    }

    fun clearCart() {
        _cartItems.value = emptyList()
        _totalPrice.value = 0.0
    }
}
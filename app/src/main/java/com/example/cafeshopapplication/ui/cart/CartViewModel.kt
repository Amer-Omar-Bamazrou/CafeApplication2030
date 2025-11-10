package com.example.cafeshopapplication.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafeshopapplication.data.model.CartItem
import com.example.cafeshopapplication.data.model.Product

class CartViewModel : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>(emptyList())

    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _totalPrice = MutableLiveData<Double>(0.0)

    val totalPrice: LiveData<Double> = _totalPrice

    fun addToCart(product: Product) {
        val currentList = _cartItems.value.orEmpty().toMutableList()

        val existingItem = currentList.find { it.product.idProd == product.idProd }

        if (existingItem != null) {
            existingItem.quantity++
        } else {
            currentList.add(CartItem(product = product, quantity = 1))
        }

        // Update the LiveData with the new list
        _cartItems.value = currentList

        calculateTotalPrice()
    }

    private fun calculateTotalPrice() {
        val list = _cartItems.value.orEmpty()
        var total = 0.0
        for (item in list) {
            total += item.product.priceProd * item.quantity
        }
        _totalPrice.value = total
    }

}
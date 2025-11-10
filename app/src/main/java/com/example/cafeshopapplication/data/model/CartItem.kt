package com.example.cafeshopapplication.data.model// Add a primary constructor that requires a Product
data class CartItem(
    val product: Product? = null,
    var quantity: Int = 0
)
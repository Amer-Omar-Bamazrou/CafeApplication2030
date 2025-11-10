package com.example.cafeshopapplication.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Order(
    @DocumentId
    val orderId: String = "",
    val cusId: String = "", // The ID of the customer who placed it
    val orderDate: Timestamp = Timestamp.now(), // Sets the date to "right now"
    var orderStatus: String = "Pending", // e.g., "Pending", "Preparing", "Collect"
    var totalPrice: Double = 0.0,

    // This will hold the list of all products in the order
    val items: List<CartItem> = emptyList()
)
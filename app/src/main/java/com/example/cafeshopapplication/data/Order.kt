package com.example.cafeshopapplication.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Order(
    @DocumentId
    val orderId: String = "",
    val cusId: String = "",
    val orderDate: Timestamp = Timestamp.now(),
    var orderStatus: String = "Pending",
    var totalPrice: Double = 0.0,
    val paymentMethod: String = "Cash",


    val items: List<CartItem> = emptyList()
)
package com.example.cafeshopapplication.data.model

import com.google.firebase.firestore.DocumentId

class Product (
    @DocumentId
    val idProd: String = "",
    val nameProd: String = "",
    val priceProd: Double = 0.0,
    val imageProd: String = "",
    val availableProd: Boolean = true
    )
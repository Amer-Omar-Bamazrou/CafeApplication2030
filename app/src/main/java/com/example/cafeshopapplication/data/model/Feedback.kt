package com.example.cafeshopapplication.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Feedback(
    @DocumentId
    val feedbackId: String = "",
    val orderId: String = "",
    val cusId: String = "",

    // Customer Details
    val customerName: String = "",
    val visitDate: String = "",
    val phone: String = "",

    // Ratings (1-5 stars)
    val appExperienceRating: Float = 0.0f,
    val productQualityRating: Float = 0.0f,
    val serviceRating: Float = 0.0f,

    // Yes/No Questions
    val isPriceReasonable: String = "",
    val willRecommend: String = "",

    // Text Comments
    val suggestions: String = "",

    val feedbackDate: Timestamp = Timestamp.now()
)
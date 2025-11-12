package com.example.cafeshopapplication.ui.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafeshopapplication.data.model.Feedback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FeedbackViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _submissionStatus = MutableLiveData<Boolean>()
    val submissionStatus: LiveData<Boolean> = _submissionStatus

    fun submitFeedback(
        orderId: String,
        name: String,
        date: String,
        phone: String,
        appExperience: Float,
        productQuality: Float,
        service: Float,
        priceReasonable: String,
        recommend: String,
        suggestions: String
    ) {
        val userId = auth.currentUser?.uid ?: return

        // Create the Feedback object using the data from the UI
        val feedback = Feedback(
            orderId = orderId,
            cusId = userId,
            customerName = name,
            visitDate = date,
            phone = phone,
            appExperienceRating = appExperience,
            productQualityRating = productQuality,
            serviceRating = service,
            isPriceReasonable = priceReasonable,
            willRecommend = recommend,
            suggestions = suggestions
        )

        // Save it to the "feedback" collection in Firestore
        db.collection("feedback").add(feedback)
            .addOnSuccessListener {
                _submissionStatus.value = true // Success!
            }
            .addOnFailureListener {
                _submissionStatus.value = false // Failure
            }
    }
}
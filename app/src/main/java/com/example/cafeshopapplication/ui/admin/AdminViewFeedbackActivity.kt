package com.example.cafeshopapplication.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafeshopapplication.databinding.ActivityAdminViewFeedbackBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.example.cafeshopapplication.data.model.Feedback // Assuming you have this model

class AdminViewFeedbackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminViewFeedbackBinding
    private lateinit var feedbackAdapter: FeedbackAdapter // We will create this next
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminViewFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchFeedback()
    }

    private fun setupRecyclerView() {
        feedbackAdapter = FeedbackAdapter(emptyList())
        binding.recyclerViewFeedback.apply {
            layoutManager = LinearLayoutManager(this@AdminViewFeedbackActivity)
            adapter = feedbackAdapter
        }
    }

    private fun fetchFeedback() {
        db.collection("feedback")
            .get()
            .addOnSuccessListener { documents ->
                val feedbackList = documents.toObjects(Feedback::class.java)
                feedbackAdapter.updateData(feedbackList)
            }
            .addOnFailureListener {
                // Handle error
            }
    }
}

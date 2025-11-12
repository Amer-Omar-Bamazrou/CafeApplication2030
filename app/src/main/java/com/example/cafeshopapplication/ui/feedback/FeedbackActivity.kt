package com.example.cafeshopapplication.ui.feedback

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeshopapplication.databinding.ActivityFeedbackBinding

class FeedbackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedbackBinding
    private val viewModel: FeedbackViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val orderId = intent.getStringExtra("ORDER_ID") ?: ""

        if (orderId.isEmpty()) {
            Toast.makeText(this, "Error: Order ID missing", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.buttonSubmitFeedback.setOnClickListener {
            submitForm(orderId)
        }

        viewModel.submissionStatus.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Error submitting feedback. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun submitForm(orderId: String) {
        // Get text data
        val name = binding.etCustomerName.text.toString().trim()
        val date = binding.etVisitDate.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val suggestions = binding.etSuggestions.text.toString().trim()

        // Get Ratings
        val appExperience = binding.ratingAppExperience.rating
        val productQuality = binding.ratingProductQuality.rating
        val service = binding.ratingService.rating

        // Get Radio Buttons (Price)
        val selectedPriceId = binding.radioGroupPrice.checkedRadioButtonId
        val priceReasonable = if (selectedPriceId != -1) {
            findViewById<RadioButton>(selectedPriceId).text.toString()
        } else "Not Answered"

        // Get Radio Buttons (Recommend)
        val selectedRecId = binding.radioGroupRecommend.checkedRadioButtonId
        val recommend = if (selectedRecId != -1) {
            findViewById<RadioButton>(selectedRecId).text.toString()
        } else "Not Answered"

        // validation check: ensure at least a name is entered
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            return
        }

        // Send all data to the ViewModel
        viewModel.submitFeedback(
            orderId, name, date, phone,
            appExperience, productQuality, service,
            priceReasonable, recommend, suggestions
        )
    }
}
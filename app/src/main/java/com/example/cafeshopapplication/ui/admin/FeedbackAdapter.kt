package com.example.cafeshopapplication.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeshopapplication.data.model.Feedback
import com.example.cafeshopapplication.databinding.ItemFeedbackBinding

class FeedbackAdapter(private var feedbackList: List<Feedback>) : RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>() {

    inner class FeedbackViewHolder(private val binding: ItemFeedbackBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(feedback: Feedback) {
            binding.textViewFeedbackOrderId.text = "Order ID: ${feedback.orderId}"

            // --- FIX: Use the new field names ---
            // Since we have 3 ratings now, let's just show the Average or one of them
            val avgRating = (feedback.appExperienceRating + feedback.productQualityRating + feedback.serviceRating) / 3
            binding.textViewFeedbackRating.text = String.format("Avg Rating: %.1f / 5", avgRating)

            // --- FIX: Use the new field name for comments ---
            binding.textViewFeedbackComment.text = feedback.suggestions
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val binding = ItemFeedbackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedbackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        holder.bind(feedbackList[position])
    }

    override fun getItemCount() = feedbackList.size

    fun updateData(newFeedbackList: List<Feedback>) {
        this.feedbackList = newFeedbackList
        notifyDataSetChanged()
    }
}
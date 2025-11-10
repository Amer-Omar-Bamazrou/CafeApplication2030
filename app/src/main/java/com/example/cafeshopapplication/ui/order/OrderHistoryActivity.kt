package com.example.cafeshopapplication.ui.order

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafeshopapplication.databinding.OrderHistoryActivityBinding
// 1. We must import FirebaseAuth
import com.google.firebase.auth.FirebaseAuth

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var binding: OrderHistoryActivityBinding
    private val viewModel: OrderHistoryViewModel by viewModels()
    private lateinit var orderAdapter: OrderHistoryAdapter

    // 2. Get the Firebase Auth instance
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OrderHistoryActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        // 3. --- THIS IS THE FIX ---
        // Get the current user ID
        val userId = auth.currentUser?.uid

        if (userId != null) {
            // 4. If the ID is not null, pass it to the ViewModel
            viewModel.listenForOrderHistory(userId)
        } else {
            // 5. If the user is somehow not logged in, show an error
            Toast.makeText(this, "Error: User not logged in", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerView() {
        orderAdapter = OrderHistoryAdapter()
        binding.recyclerViewOrderHistory.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(this@OrderHistoryActivity)
        }
    }

    private fun observeViewModel() {
        viewModel.orders.observe(this) { orderList ->
            orderAdapter.updateData(orderList)
        }
    }
}
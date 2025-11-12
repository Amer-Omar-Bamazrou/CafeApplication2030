package com.example.cafeshopapplication.ui.order

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafeshopapplication.databinding.OrderHistoryActivityBinding
import com.google.firebase.auth.FirebaseAuth

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var binding: OrderHistoryActivityBinding
    private val viewModel: OrderHistoryViewModel by viewModels()
    private lateinit var orderAdapter: OrderHistoryAdapter

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OrderHistoryActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        val userId = auth.currentUser?.uid

        if (userId != null) {
            viewModel.listenForOrderHistory(userId)
        } else {
            //  show an error
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
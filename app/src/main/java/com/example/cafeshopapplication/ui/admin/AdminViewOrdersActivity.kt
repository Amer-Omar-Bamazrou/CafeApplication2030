package com.example.cafeshopapplication.ui.admin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafeshopapplication.data.model.Order
import com.example.cafeshopapplication.databinding.AdminViewOrdersActivityBinding
import com.google.firebase.firestore.FirebaseFirestore

class AdminViewOrdersActivity : AppCompatActivity() {

    private lateinit var binding: AdminViewOrdersActivityBinding
    private val viewModel: AdminViewOrdersViewModel by viewModels()
    private lateinit var ordersAdapter: AdminViewOrdersAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminViewOrdersActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupRecyclerView()

        observeViewModel()

        viewModel.listenForAllOrders()
    }

    private fun setupRecyclerView() {

        ordersAdapter = AdminViewOrdersAdapter { order, newStatus ->
            updateOrderStatus(order, newStatus)
        }

        binding.recyclerViewAdminOrders.apply {
            adapter = ordersAdapter
            layoutManager = LinearLayoutManager(this@AdminViewOrdersActivity)
        }
    }

    private fun observeViewModel() {

        viewModel.orders.observe(this) { orderList ->
            ordersAdapter.updateData(orderList)
        }
    }

    private fun updateOrderStatus(order: Order, newStatus: String) {
        if (order.orderId.isEmpty()) return

        db.collection("orders").document(order.orderId)
            .update("orderStatus", newStatus)
            .addOnSuccessListener {
                Toast.makeText(this, "Order status updated to $newStatus", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to update status", Toast.LENGTH_SHORT).show()
            }
    }
}
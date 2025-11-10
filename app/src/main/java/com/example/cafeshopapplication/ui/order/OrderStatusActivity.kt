package com.example.cafeshopapplication.ui.order

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafeshopapplication.databinding.OrderStatusActivityBinding
import com.example.cafeshopapplication.ui.menu.MenuPage
import java.text.SimpleDateFormat
import java.util.Locale

// --- FIX: ADDED MISSING IMPORTS ---
import com.example.cafeshopapplication.ui.order.OrderDetailAdapter
import com.example.cafeshopapplication.ui.order.OrderStatusViewModel
// ----------------------------------


class OrderStatusActivity : AppCompatActivity() {

    private lateinit var binding: OrderStatusActivityBinding
    private val viewModel: OrderStatusViewModel by viewModels()

    private lateinit var detailAdapter: OrderDetailAdapter // This name is now correct

    private val dateFormatter = SimpleDateFormat("dd MMM yyyy, h:mm a", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OrderStatusActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        val orderId = intent.getStringExtra("ORDER_ID")

        if (orderId == null || orderId.isEmpty()) {
            Toast.makeText(this, "Error: Could not find order ID", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        viewModel.listenToOrderStatus(orderId)
        observeViewModel()

        binding.buttonBackToMenu.setOnClickListener {
            val intent = Intent(this, MenuPage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        detailAdapter = OrderDetailAdapter()
        binding.recyclerViewOrderItems.apply {
            adapter = detailAdapter
            layoutManager = LinearLayoutManager(this@OrderStatusActivity)
        }
    }

    private fun observeViewModel() {
        viewModel.order.observe(this) { order ->
            if (order != null) {
                // Set the main status and total
                binding.textViewOrderStatus.text = order.orderStatus

                // Clean up string formatting
                val totalText = String.format(Locale.getDefault(), "TOTAL: $%.2f", order.totalPrice)
                binding.textViewTotalPrice.text = totalText

                val dateText = "Placed on: ${dateFormatter.format(order.orderDate.toDate())}"
                binding.textViewOrderDate.text = dateText

                // Update the list of items in the order
                detailAdapter.updateData(order.items)
            } else {
                Toast.makeText(this, "Error: Order could not be loaded", Toast.LENGTH_LONG).show()
            }
        }
    }
}
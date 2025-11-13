package com.example.cafeshopapplication.ui.order

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafeshopapplication.databinding.OrderStatusActivityBinding
import com.example.cafeshopapplication.MainActivity
import com.example.cafeshopapplication.ui.feedback.FeedbackActivity
import java.text.SimpleDateFormat
import java.util.Locale

class OrderStatusActivity : AppCompatActivity() {

    private lateinit var binding: OrderStatusActivityBinding
    private val viewModel: OrderStatusViewModel by viewModels()
    private lateinit var detailAdapter: OrderDetailAdapter
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
            val intent = Intent(this, MainActivity::class.java)
            // Clear the back stack so the user can't press 'back' to return here
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        // ----------------------------

        binding.buttonRateOrder.setOnClickListener {
            val intent = Intent(this, FeedbackActivity::class.java)
            intent.putExtra("ORDER_ID", orderId)
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
                binding.textViewOrderStatus.text = order.orderStatus
                binding.textViewTotalPrice.text = String.format(Locale.getDefault(), "TOTAL: $%.2f", order.totalPrice)
                binding.textViewOrderDate.text = "Placed on: ${dateFormatter.format(order.orderDate.toDate())}"

                detailAdapter.updateData(order.items)

                if (order.orderStatus == "Completed") {
                    binding.buttonRateOrder.visibility = View.VISIBLE
                } else {
                    binding.buttonRateOrder.visibility = View.GONE
                }

            } else {
                Toast.makeText(this, "Error: Order could not be loaded", Toast.LENGTH_LONG).show()
            }
        }
    }
}
package com.example.cafeshopapplication.ui.cart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafeshopapplication.databinding.CartActivityBinding
// 1. --- IMPORT THE NEW (but not yet created) ACTIVITY ---
import com.example.cafeshopapplication.ui.order.OrderStatusActivity
import com.example.cafeshopapplication.ui.order.OrderViewModel
import com.google.firebase.auth.FirebaseAuth

class CartActivity : AppCompatActivity() {

    private lateinit var binding: CartActivityBinding
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private val orderViewModel: OrderViewModel by viewModels()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = CartActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        binding.buttonPlaceOrder.setOnClickListener {
            if (auth.currentUser != null) {
                orderViewModel.placeOrder()
            } else {
                Toast.makeText(this, "Error: You are not logged in.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(cartViewModel = cartViewModel)
        binding.recyclerViewCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(this@CartActivity)
        }
    }

    private fun observeViewModel() {
        // (cartItems.observe and totalPrice.observe are the same)
        cartViewModel.cartItems.observe(this) { cartList ->
            cartAdapter.updateData(cartList)
            binding.buttonPlaceOrder.isEnabled = cartList.isNotEmpty()
        }

        cartViewModel.totalPrice.observe(this) { price ->
            binding.textViewTotalPrice.text = String.format("Total: $%.2f", price)
        }

        // --- 2. THIS IS THE MODIFICATION ---
        // 'orderId' is now a String? (nullable string), not a Boolean
        orderViewModel.orderStatus.observe(this) { orderId ->
            if (orderId != null) {
                // Order was successful!
                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_LONG).show()

                // Go to the new Order Status page
                val intent = Intent(this, OrderStatusActivity::class.java)

                // --- 3. PASS THE NEW ORDER ID TO THE STATUS PAGE ---
                intent.putExtra("ORDER_ID", orderId)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            } else {
                // Order failed (orderId was null)
                Toast.makeText(this, "Order failed. Please try again.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
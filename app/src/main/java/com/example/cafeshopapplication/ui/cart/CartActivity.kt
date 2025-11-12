package com.example.cafeshopapplication.ui.cart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafeshopapplication.R
import com.example.cafeshopapplication.databinding.CartActivityBinding
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
                val selectedId = binding.radioGroupPayment.checkedRadioButtonId

                val paymentMethod = if (selectedId == R.id.radio_card) {
                    "Card"
                } else {
                    "Cash"
                }

                orderViewModel.placeOrder(paymentMethod)
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
        cartViewModel.cartItems.observe(this) { cartList ->
            cartAdapter.updateData(cartList)
            binding.buttonPlaceOrder.isEnabled = cartList.isNotEmpty()
        }

        cartViewModel.totalPrice.observe(this) { price ->
            binding.textViewTotalPrice.text = String.format("Total: $%.2f", price)
        }

        orderViewModel.orderStatus.observe(this) { orderId ->
            if (orderId != null) {
                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_LONG).show()

                val intent = Intent(this, OrderStatusActivity::class.java)
                intent.putExtra("ORDER_ID", orderId)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            } else {
                Toast.makeText(this, "Order failed. Please try again.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
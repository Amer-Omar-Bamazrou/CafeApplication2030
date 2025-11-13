package com.example.cafeshopapplication.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafeshopapplication.R
import com.example.cafeshopapplication.databinding.CartActivityBinding
import com.example.cafeshopapplication.ui.order.OrderStatusActivity
import com.example.cafeshopapplication.ui.order.OrderViewModel
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent

class CartFragment : Fragment() {

    private var _binding: CartActivityBinding? = null
    private val binding get() = _binding!!

    // ViewModels use 'viewModels()' in Fragments
    private val cartViewModel: CartViewModel by viewModels()
    private val orderViewModel: OrderViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize binding
        _binding = CartActivityBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()
        observeViewModel()

        // Button Listener (Uses requireContext()
        binding.buttonPlaceOrder.setOnClickListener {
            if (auth.currentUser != null) {
                // Check payment method
                val selectedId = binding.radioGroupPayment.checkedRadioButtonId
                val paymentMethod = if (selectedId == R.id.radio_card) "Card" else "Cash"

                // Pass the payment method to the ViewModel
                orderViewModel.placeOrder(paymentMethod)
            } else {
                Toast.makeText(requireContext(), "Error: You are not logged in.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun setupRecyclerView() {
        // Use requireContext() for the LayoutManager
        cartAdapter = CartAdapter(cartViewModel = cartViewModel)
        binding.recyclerViewCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        // Use viewLifecycleOwner for observing LiveData in a Fragment
        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartList ->
            cartAdapter.updateData(cartList)
            binding.buttonPlaceOrder.isEnabled = cartList.isNotEmpty()
        }

        cartViewModel.totalPrice.observe(viewLifecycleOwner) { price ->
            binding.textViewTotalPrice.text = String.format("Total: $%.2f", price)
        }

        orderViewModel.orderStatus.observe(viewLifecycleOwner) { orderId ->
            if (orderId != null) {
                Toast.makeText(requireContext(), "Order placed successfully!", Toast.LENGTH_LONG).show()

                // Status page (Still a separate Activity)
                val intent = Intent(requireContext(), OrderStatusActivity::class.java)
                intent.putExtra("ORDER_ID", orderId)

                // Clear the cart page from memory
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            } else {
                Toast.makeText(requireContext(), "Order failed. Please try again.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
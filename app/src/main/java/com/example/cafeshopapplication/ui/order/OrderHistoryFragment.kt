package com.example.cafeshopapplication.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafeshopapplication.databinding.OrderHistoryActivityBinding
import com.google.firebase.auth.FirebaseAuth

class OrderHistoryFragment : Fragment() {

    private var _binding: OrderHistoryActivityBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderHistoryViewModel by viewModels()
    private lateinit var orderAdapter: OrderHistoryAdapter
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize binding
        _binding = OrderHistoryActivityBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()
        observeViewModel()

        // Get the user ID and fetch orders
        val userId = auth.currentUser?.uid

        if (userId != null) {
            viewModel.listenForOrderHistory(userId)
        } else {
            Toast.makeText(requireContext(), "Error: User not logged in", Toast.LENGTH_LONG).show()
        }

        return view
    }

    private fun setupRecyclerView() {
        // Use requireContext() for the LayoutManager
        orderAdapter = OrderHistoryAdapter()
        binding.recyclerViewOrderHistory.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        // Use viewLifecycleOwner for observing LiveData in a Fragment
        viewModel.orders.observe(viewLifecycleOwner) { orderList ->
            orderAdapter.updateData(orderList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
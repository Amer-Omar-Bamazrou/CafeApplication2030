package com.example.cafeshopapplication.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cafeshopapplication.R
import com.example.cafeshopapplication.databinding.MenuPageBinding
import com.example.cafeshopapplication.ui.cart.CartViewModel
import com.example.cafeshopapplication.ui.feedback.FeedbackActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuFragment : Fragment() {

    private var _binding: MenuPageBinding? = null
    private val binding get() = _binding!!

    private val menuViewModel: MenuViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MenuPageBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()
        observeViewModel()

        // 1. CATEGORY CHIP LISTENERS
        binding.chipAll.setOnClickListener { menuViewModel.filterByCategory("All") }
        binding.chipLatte.setOnClickListener { menuViewModel.filterByCategory("Latte") }
        binding.chipMacchiato.setOnClickListener { menuViewModel.filterByCategory("Macchiato") }
        binding.chipCake.setOnClickListener { menuViewModel.filterByCategory("Cake") }

        binding.buttonFeedbackShortcut.setOnClickListener {
            // Open FeedbackActivity with a generic ID
            val intent = Intent(requireContext(), FeedbackActivity::class.java)
            intent.putExtra("ORDER_ID", "GENERAL_FEEDBACK")
            startActivity(intent)
        }

        return view
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(cartViewModel = cartViewModel)

        binding.recyclerViewProducts.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun observeViewModel() {
        menuViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        menuViewModel.products.observe(viewLifecycleOwner) { productList ->
            productAdapter.updateData(productList)
        }

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartList ->
            if (cartList.isNotEmpty()) {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.cafeshopapplication.ui.menu

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafeshopapplication.databinding.MenuPageBinding
import com.example.cafeshopapplication.ui.menu.MenuViewModel
import com.example.cafeshopapplication.ui.menu.ProductAdapter


class MenuPage : AppCompatActivity() {

    private lateinit var binding: MenuPageBinding
    private val menuViewModel: MenuViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MenuPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter()

        binding.recyclerViewProducts.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(this@MenuPage)
        }
    }

    private fun observeViewModel() {
        menuViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        menuViewModel.products.observe(this) { productList ->
            productAdapter.updateData(productList)
        }
    }
}
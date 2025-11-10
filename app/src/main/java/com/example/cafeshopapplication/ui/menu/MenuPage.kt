package com.example.cafeshopapplication.ui.menu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafeshopapplication.ui.cart.CartActivity
import com.example.cafeshopapplication.databinding.MenuPageBinding
import com.example.cafeshopapplication.ui.cart.CartViewModel
import com.example.cafeshopapplication.ui.menu.MenuViewModel
import com.example.cafeshopapplication.ui.menu.ProductAdapter

class MenuPage : AppCompatActivity() {

    private lateinit var binding: MenuPageBinding
    private val menuViewModel: MenuViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MenuPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        binding.buttonViewCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(cartViewModel = cartViewModel)

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

        cartViewModel.cartItems.observe(this) { cartList ->
            if (cartList.isNotEmpty()) {
                Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
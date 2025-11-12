package com.example.cafeshopapplication.ui.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafeshopapplication.data.model.Product
import com.example.cafeshopapplication.databinding.AdminManageMenuActivityBinding
import com.example.cafeshopapplication.ui.menu.MenuViewModel
import com.google.firebase.firestore.FirebaseFirestore

class AdminManageMenuActivity : AppCompatActivity() {

    private lateinit var binding: AdminManageMenuActivityBinding
    private lateinit var adminAdapter: AdminManageMenuAdapter

    private val menuViewModel: MenuViewModel by viewModels()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminManageMenuActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        binding.buttonAddNewProduct.setOnClickListener {
            startActivity(Intent(this, AdminAddProductActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        menuViewModel.fetchProducts()
    }

    private fun setupRecyclerView() {
        adminAdapter = AdminManageMenuAdapter(
            onEditClick = { product ->
                // TODO: We will build the "Edit" page in a later step
                Toast.makeText(this, "Edit: ${product.nameProd}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { product ->
                showDeleteConfirmation(product)
            }
        )

        binding.recyclerViewAdminProducts.apply {
            adapter = adminAdapter
            layoutManager = LinearLayoutManager(this@AdminManageMenuActivity)
        }
    }

    private fun observeViewModel() {
        // Observe the product list from the ViewModel
        menuViewModel.products.observe(this) { productList ->
            adminAdapter.updateData(productList)
        }
    }

    private fun showDeleteConfirmation(product: Product) {
        AlertDialog.Builder(this)
            .setTitle("Delete Product")
            .setMessage("Are you sure you want to delete ${product.nameProd}?")
            .setPositiveButton("Delete") { _, _ ->
                deleteProduct(product)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteProduct(product: Product) {
        if (product.idProd.isEmpty()) return

        db.collection("products").document(product.idProd)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Product deleted", Toast.LENGTH_SHORT).show()
                menuViewModel.fetchProducts() // Refresh the list
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error deleting product", Toast.LENGTH_SHORT).show()
            }
    }
}
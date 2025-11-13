package com.example.cafeshopapplication.ui.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeshopapplication.data.model.Product
import com.example.cafeshopapplication.databinding.AdminEditProductActivityBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AdminEditProductActivity : AppCompatActivity() {

    private lateinit var binding: AdminEditProductActivityBinding
    private val db = FirebaseFirestore.getInstance()
    private var currentProductId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminEditProductActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentProductId = intent.getStringExtra("PRODUCT_ID")

        if (currentProductId == null) {
            Toast.makeText(this, "Error: No product ID found", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        loadProductData(currentProductId!!)

        binding.buttonUpdateProduct.setOnClickListener {
            // update logic
        }

    }

    private fun loadProductData(productId: String) {
        db.collection("products").document(productId).get()
            .addOnSuccessListener { document ->
                val product = document.toObject(Product::class.java)
                if (product != null) {
                    binding.editTextProductName.setText(product.nameProd)
                    binding.editTextProductPrice.setText(product.priceProd.toString())
                    binding.editTextProductImageUrl.setText(product.imageProd)
                    binding.checkboxIsAvailable.isChecked = product.availableProd
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error loading product data", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateProduct() {
        val productId = currentProductId ?: return

        val name = binding.editTextProductName.text.toString().trim()
        val priceString = binding.editTextProductPrice.text.toString().trim()
        val imageUrl = binding.editTextProductImageUrl.text.toString().trim()
        val isAvailable = binding.checkboxIsAvailable.isChecked

        if (name.isEmpty() || priceString.isEmpty()) {
            Toast.makeText(this, "Please enter a name and price", Toast.LENGTH_SHORT).show()
            return
        }
        val price = priceString.toDoubleOrNull()
        if (price == null || price <= 0) {
            Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedProduct = Product(
            idProd = productId,
            nameProd = name,
            priceProd = price,
            imageProd = imageUrl,
            availableProd = isAvailable
        )

        db.collection("products").document(productId)
            .set(updatedProduct)
            .addOnSuccessListener {
                Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error updating product", Toast.LENGTH_LONG).show()
            }
    }
}
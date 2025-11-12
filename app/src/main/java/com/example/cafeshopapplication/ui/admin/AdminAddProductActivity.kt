package com.example.cafeshopapplication.ui.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeshopapplication.data.model.Product
import com.example.cafeshopapplication.databinding.AdminAddProductActivityBinding
import com.google.firebase.firestore.FirebaseFirestore

class AdminAddProductActivity : AppCompatActivity() {

    private lateinit var binding: AdminAddProductActivityBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminAddProductActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSaveProduct.setOnClickListener {
            saveNewProduct()
        }
    }

    private fun saveNewProduct() {
        //  Get the data from the form
        val name = binding.editTextProductName.text.toString().trim()
        val priceString = binding.editTextProductPrice.text.toString().trim()
        val imageUrl = binding.editTextProductImageUrl.text.toString().trim()
        val isAvailable = binding.checkboxIsAvailable.isChecked

        // Simple validation
        if (name.isEmpty() || priceString.isEmpty()) {
            Toast.makeText(this, "Please enter a name and price", Toast.LENGTH_SHORT).show()
            return
        }

        val price = priceString.toDoubleOrNull()
        if (price == null || price <= 0) {
            Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show()
            return
        }

        // Create the new Product object (using your field names)
        val newProduct = Product(
            nameProd = name,
            priceProd = price,
            imageProd = imageUrl,
            availableProd = isAvailable
        )

        //Save the product to the "products" collection
        db.collection("products")
            .add(newProduct)
            .addOnSuccessListener {
                Toast.makeText(this, "Product saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error saving product: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
package com.example.cafeshopapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeshopapplication.data.model.Product
import com.example.cafeshopapplication.databinding.ItemDetailsPageBinding // Make sure this matches your XML file name
import com.example.cafeshopapplication.ui.cart.CartViewModel
import com.example.cafeshopapplication.ui.menu.ProductDetailsViewModel

class ItemDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ItemDetailsPageBinding

    // 1. Get our ViewModels
    private val detailsViewModel: ProductDetailsViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    // 2. A variable to hold the currently loaded product
    private var currentProduct: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This line assumes your layout file is 'item_details_page.xml'
        binding = ItemDetailsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3. Get the Product ID from the Intent
        val productId = intent.getStringExtra("PRODUCT_ID")

        if (productId == null) {
            // If no ID was sent, show an error and close the page
            Toast.makeText(this, "Error: Product not found", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // 4. Tell the ViewModel to fetch the product
        detailsViewModel.fetchProductDetails(productId)

        // 5. Observe the LiveData
        observeViewModel()

        // 6. Set up the 'Add to Cart' button
        binding.addToCartButton.setOnClickListener {
            currentProduct?.let { product ->
                cartViewModel.addToCart(product)
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
                finish() // Close the details page and go back to the menu
            }
        }
    }

    private fun observeViewModel() {
        detailsViewModel.product.observe(this) { product ->
            if (product != null) {
                // 7. When the product is loaded, save it
                currentProduct = product

                // 8. Set the data in the layout
                binding.itemName.text = product.nameProd
                binding.itemPrice.text = String.format("$%.2f", product.priceProd)

                // TODO: We will add the description and image loading later
                // binding.itemDescription.text = "Product description from database"
                // Glide.with(this).load(product.imageProd).into(binding.itemImage)

            } else {
                // Handle the case where the product isn't found
                Toast.makeText(this, "Error: Product details could not be loaded", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}
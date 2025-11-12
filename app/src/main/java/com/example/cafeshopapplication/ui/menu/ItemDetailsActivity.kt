package com.example.cafeshopapplication.ui.menu

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cafeshopapplication.data.model.Product
import com.example.cafeshopapplication.databinding.ItemDetailsPageBinding
import com.example.cafeshopapplication.ui.cart.CartViewModel

class ItemDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ItemDetailsPageBinding

    private val detailsViewModel: ProductDetailsViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    private var currentProduct: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ItemDetailsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getStringExtra("PRODUCT_ID")

        if (productId == null) {
            Toast.makeText(this, "Error: Product not found", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        //  the ViewModel to fetch the product
        detailsViewModel.fetchProductDetails(productId)

        // Observe the LiveData
        observeViewModel()

        // Set up the 'Add to Cart' button
        binding.addToCartButton.setOnClickListener {
            currentProduct?.let { product ->
                cartViewModel.addToCart(product)
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
                finish() // Close the details page
            }
        }
    }

    private fun observeViewModel() {
        detailsViewModel.product.observe(this) { product ->
            if (product != null) {
                // Save the product
                currentProduct = product

                // Set text data
                binding.itemName.text = product.nameProd
                binding.itemPrice.text = String.format("$%.2f", product.priceProd)

                binding.itemDescription.text = product.description

                binding.itemRatingBar.rating = product.rating.toFloat()
                binding.itemRatingNumber.text = product.rating.toString()


                Glide.with(this)
                    .load(product.imageProd)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(binding.itemImage)

            } else {
                Toast.makeText(this, "Error: Product details could not be loaded", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}
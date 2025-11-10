package com.example.cafeshopapplication.ui.menu

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeshopapplication.data.model.Product
import com.example.cafeshopapplication.databinding.ItemProductBinding
// We need to import the CartViewModel AND the DetailsActivity
import com.example.cafeshopapplication.ui.cart.CartViewModel
import com.example.cafeshopapplication.ItemDetailsActivity // <-- Assumes this is your file name

class ProductAdapter(
    private var productList: List<Product> = emptyList(),
    // We need the CartViewModel for the '+' button
    private val cartViewModel: CartViewModel
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.binding.textViewProductName.text = product.nameProd
        holder.binding.textViewProductPrice.text = String.format("$%.2f", product.priceProd)

        // --- CLICK LISTENER 1: The '+' Button ---
        holder.binding.buttonAddToCart.setOnClickListener {
            // This is the "Quick Add"
            cartViewModel.addToCart(product)
        }

        // --- CLICK LISTENER 2: The Whole Card ---
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context

            // Go to the Details Page
            val intent = Intent(context, ItemDetailsActivity::class.java)

            // Pass the ID of the product that was clicked
            intent.putExtra("PRODUCT_ID", product.idProd)

            context.startActivity(intent)
        }
    }

    fun updateData(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }
}
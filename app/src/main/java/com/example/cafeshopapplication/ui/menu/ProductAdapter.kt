package com.example.cafeshopapplication.ui.menu

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cafeshopapplication.data.model.Product
import com.example.cafeshopapplication.databinding.ItemProductBinding

import com.example.cafeshopapplication.ui.cart.CartViewModel
import com.example.cafeshopapplication.ui.menu.ItemDetailsActivity

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

        Glide.with(holder.itemView.context)
            .load(product.imageProd)
            .placeholder(android.R.drawable.ic_menu_gallery) // Shows while loading
            .error(android.R.drawable.ic_menu_report_image) // Shows if link is bad
            .into(holder.binding.imageViewProduct)

        holder.binding.buttonAddToCart.setOnClickListener {
            cartViewModel.addToCart(product)
        }

        // View Details
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ItemDetailsActivity::class.java)
            intent.putExtra("PRODUCT_ID", product.idProd)
            context.startActivity(intent)
        }
    }

    fun updateData(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }
}
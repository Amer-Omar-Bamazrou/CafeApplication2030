package com.example.cafeshopapplication.ui.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeshopapplication.data.model.Product
import com.example.cafeshopapplication.databinding.ItemProductBinding

class ProductAdapter(
    private var productList: List<Product> = emptyList()
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    // This creates the new card layout
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

    }

    fun updateData(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }
}
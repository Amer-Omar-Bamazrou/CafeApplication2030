package com.example.cafeshopapplication.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeshopapplication.data.model.CartItem
import com.example.cafeshopapplication.databinding.ItemOrderDetailBinding

class OrderDetailAdapter(
    private var itemList: List<CartItem> = emptyList()
) : RecyclerView.Adapter<OrderDetailAdapter.DetailViewHolder>() {

    inner class DetailViewHolder(val binding: ItemOrderDetailBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding = ItemOrderDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DetailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val cartItem = itemList[position]

        val subtotal = cartItem.product?.priceProd?.times(cartItem.quantity) ?: 0.0

        holder.binding.textViewDetailQuantity.text = "x${cartItem.quantity}"
        holder.binding.textViewDetailName.text = cartItem.product?.nameProd ?: "Unknown Item"
        holder.binding.textViewDetailSubtotal.text = String.format("$%.2f", subtotal)
    }

    fun updateData(newList: List<CartItem>) {
        itemList = newList
        notifyDataSetChanged()
    }
}
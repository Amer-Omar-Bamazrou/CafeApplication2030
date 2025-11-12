package com.example.cafeshopapplication.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeshopapplication.data.model.CartItem
import com.example.cafeshopapplication.databinding.ItemCartBinding
import com.example.cafeshopapplication.ui.cart.CartViewModel

class CartAdapter(
    private var cartList: List<CartItem> = emptyList(),
    private val cartViewModel: CartViewModel
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartList[position]

        // This adds '?' and '?:' to handle a null product
        holder.binding.textViewItemName.text = cartItem.product?.nameProd ?: "Unknown Item"
        holder.binding.textViewItemPrice.text = String.format("$%.2f", cartItem.product?.priceProd ?: 0.0)
        holder.binding.textViewQuantity.text = cartItem.quantity.toString()


        holder.binding.buttonRemoveItem.setOnClickListener {
            cartViewModel.removeItemFromCart(cartItem)
        }

        holder.binding.buttonIncreaseQuantity.setOnClickListener {
            val newQuantity = cartItem.quantity + 1
            cartViewModel.updateItemQuantity(cartItem, newQuantity)
        }

        holder.binding.buttonDecreaseQuantity.setOnClickListener {
            val newQuantity = cartItem.quantity - 1
            cartViewModel.updateItemQuantity(cartItem, newQuantity)
        }
    }

    fun updateData(newList: List<CartItem>) {
        cartList = newList
        notifyDataSetChanged()
    }
}
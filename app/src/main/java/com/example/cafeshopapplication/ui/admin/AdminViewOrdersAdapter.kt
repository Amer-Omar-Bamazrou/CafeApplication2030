package com.example.cafeshopapplication.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeshopapplication.data.model.Order
import com.example.cafeshopapplication.databinding.ItemAdminOrderBinding
import java.text.SimpleDateFormat
import java.util.Locale

class AdminViewOrdersAdapter(
    private var orderList: List<Order> = emptyList(),
    private val onUpdateStatus: (order: Order, newStatus: String) -> Unit
) : RecyclerView.Adapter<AdminViewOrdersAdapter.AdminOrderViewHolder>() {


    inner class AdminOrderViewHolder(val binding: ItemAdminOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminOrderViewHolder {
        val binding = ItemAdminOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AdminOrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: AdminOrderViewHolder, position: Int) {
        val order = orderList[position]


        val totalQuantity = order.items.sumOf { it.quantity }

        holder.binding.textViewOrderId.text = "Order ID: ${order.orderId}"
        holder.binding.textViewCustomerId.text = "Customer ID: ${order.cusId}"
        holder.binding.textViewTotalQuantity.text = "$totalQuantity Items"
        holder.binding.textViewOrderTotal.text = String.format("Total: $%.2f", order.totalPrice)
        holder.binding.textViewOrderStatus.text = order.orderStatus

        holder.binding.buttonSetPreparing.setOnClickListener {
            onUpdateStatus(order, "Preparing")
        }

        holder.binding.buttonSetCollect.setOnClickListener {
            onUpdateStatus(order, "Completed")
        }
    }

    fun updateData(newList: List<Order>) {
        orderList = newList
        notifyDataSetChanged()
    }
}
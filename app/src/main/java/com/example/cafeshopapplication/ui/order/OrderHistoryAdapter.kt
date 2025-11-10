package com.example.cafeshopapplication.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeshopapplication.data.model.Order
import com.example.cafeshopapplication.databinding.ItemOrderHistoryBinding
import java.text.SimpleDateFormat
import java.util.Locale

class OrderHistoryAdapter(
    private var orderList: List<Order> = emptyList()
) : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    // A standard date formatter
    private val dateFormatter = SimpleDateFormat("dd MMM yyyy, h:mm a", Locale.getDefault())

    inner class OrderHistoryViewHolder(val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val binding = ItemOrderHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderHistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        val order = orderList[position]

        // Set the data
        holder.binding.textViewOrderTotal.text = String.format("Total: $%.2f", order.totalPrice)
        holder.binding.textViewOrderStatus.text = order.orderStatus

        // Format the Timestamp from Firebase into a readable date string
        holder.binding.textViewOrderDate.text = dateFormatter.format(order.orderDate.toDate())
    }

    fun updateData(newList: List<Order>) {
        orderList = newList
        notifyDataSetChanged()
    }
}
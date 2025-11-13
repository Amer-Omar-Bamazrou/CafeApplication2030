package com.example.cafeshopapplication.ui.order

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeshopapplication.data.model.Order
import com.example.cafeshopapplication.databinding.ItemOrderHistoryBinding
import com.example.cafeshopapplication.ui.feedback.FeedbackActivity
import java.text.SimpleDateFormat
import java.util.Locale

class OrderHistoryAdapter(
    private var orderList: List<Order> = emptyList()
) : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

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

        holder.binding.textViewOrderDate.text = dateFormatter.format(order.orderDate.toDate())


        // show the "Rate Order" button if the order is "Completed"
        if (order.orderStatus == "Completed") {
            holder.binding.buttonLeaveFeedback.visibility = View.VISIBLE
        } else {
            holder.binding.buttonLeaveFeedback.visibility = View.GONE
        }

        // Click listener to open FeedbackActivity
        holder.binding.buttonLeaveFeedback.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, FeedbackActivity::class.java)
            intent.putExtra("ORDER_ID", order.orderId)
            context.startActivity(intent)
        }
    }

    fun updateData(newList: List<Order>) {
        orderList = newList
        notifyDataSetChanged()
    }
}
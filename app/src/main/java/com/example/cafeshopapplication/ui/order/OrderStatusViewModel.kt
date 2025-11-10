package com.example.cafeshopapplication.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafeshopapplication.data.model.Order
import com.google.firebase.firestore.FirebaseFirestore

class OrderStatusViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // This will hold the single, updating order
    private val _order = MutableLiveData<Order?>()
    val order: LiveData<Order?> = _order

    /**
     * This function creates a REAL-TIME listener for one specific order.
     */
    fun listenToOrderStatus(orderId: String) {
        if (orderId.isEmpty()) {
            _order.postValue(null)
            return
        }

        // Listen to a specific document: "orders" -> [orderId]
        db.collection("orders").document(orderId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _order.postValue(null) // Post null on error
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    // Convert the document to an Order object
                    val order = snapshot.toObject(Order::class.java)
                    _order.postValue(order)
                } else {
                    _order.postValue(null) // Post null if not found
                }
            }
    }
}
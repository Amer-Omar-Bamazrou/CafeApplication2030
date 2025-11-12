package com.example.cafeshopapplication.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafeshopapplication.data.model.Order
import com.google.firebase.firestore.FirebaseFirestore

class OrderStatusViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _order = MutableLiveData<Order?>()
    val order: LiveData<Order?> = _order

    //realtime listener
    fun listenToOrderStatus(orderId: String) {
        if (orderId.isEmpty()) {
            _order.postValue(null)
            return
        }

        db.collection("orders").document(orderId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _order.postValue(null)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val order = snapshot.toObject(Order::class.java)
                    _order.postValue(order)
                } else {
                    _order.postValue(null) // Post null if not found
                }
            }
    }
}
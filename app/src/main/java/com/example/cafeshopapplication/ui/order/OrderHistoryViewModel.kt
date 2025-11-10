package com.example.cafeshopapplication.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafeshopapplication.data.model.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

// We don't need coroutines for this
// import kotlinx.coroutines.launch
// import kotlinx.coroutines.tasks.await

class OrderHistoryViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    /**
     * This function now creates a REAL-TIME listener for orders.
     */
    fun listenForOrderHistory(userId: String) {

        // Build the new, simpler query
        db.collection("orders")
            .whereEqualTo("cusId", userId)

            // --- DELETE THIS LINE ---
            // .orderBy("orderDate", Query.Direction.DESCENDING)

            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _orders.postValue(emptyList())
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val orderList = snapshot.toObjects(Order::class.java)
                    _orders.postValue(orderList)
                } else {
                    _orders.postValue(emptyList())
                }
            }
    }
}
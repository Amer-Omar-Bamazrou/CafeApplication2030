package com.example.cafeshopapplication.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafeshopapplication.data.model.Customer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _authResult = MutableLiveData<AuthResult>()
    val authResult: LiveData<AuthResult> = _authResult

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authResult.value = AuthResult.Error("Please fill all fields")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authResult.value = AuthResult.Success(task.result.user!!)
                } else {
                    _authResult.value = AuthResult.Error(task.exception?.message ?: "Login failed")
                }
            }
    }
    fun register(email: String, password: String, fullName: String, address: String, phone: String) {
        if (email.isEmpty() || password.isEmpty() || fullName.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            _authResult.value = AuthResult.Error("Fill fields")
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {

                        val customer = Customer(
                            uid = userId,
                            fullName = fullName,
                            email = email,
                            address = address,
                            phoneNo = phone
                        )

                        firestore.collection("users").document(userId)
                            .set(customer)
                            .addOnSuccessListener {
                                _authResult.value = AuthResult.Success(task.result.user!!)
                            }
                            .addOnFailureListener { e ->
                                _authResult.value = AuthResult.Error("Failed to save user data: ${e.message}")
                            }
                    }
                } else {
                    _authResult.value = AuthResult.Error(task.exception?.message ?: "Registration failed")
                }
            }
    }
}

sealed class AuthResult {
    data class Success(val user: FirebaseUser) : AuthResult()
    data class Error(val message: String) : AuthResult()
}
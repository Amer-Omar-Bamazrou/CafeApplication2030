package com.example.cafeshopapplication.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafeshopapplication.data.model.Customer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

// 1. We make our class extend ViewModel()
class AuthViewModel : ViewModel() {

    // 2. Get instances of Firebase Auth and Firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // 3. This LiveData will send results back to the Activity
    private val _authResult = MutableLiveData<AuthResult>()
    val authResult: LiveData<AuthResult> = _authResult

    /**
     * This is the LOGIN logic, moved from your userLogin activity.
     */
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

    /**
     * This is the REGISTER logic, moved from your userSignUp activity.
     */
    // 4. Notice the parameters now match our new Customer model
    fun register(email: String, password: String, fullName: String, address: String, phone: String) {
        if (email.isEmpty() || password.isEmpty() || fullName.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            _authResult.value = AuthResult.Error("Please fill all fields")
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {

                        // 5. HERE! We are now using your Customer class.
                        val customer = Customer(
                            uid = userId,
                            fullName = fullName,
                            email = email,
                            address = address,
                            phoneNo = phone
                        )

                        // 6. We save the customer object to your 'users' collection
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

/**
 * This sealed class represents the results: Success or Error.
 */
sealed class AuthResult {
    data class Success(val user: FirebaseUser) : AuthResult()
    data class Error(val message: String) : AuthResult()
}
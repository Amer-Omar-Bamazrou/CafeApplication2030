package com.example.cafeshopapplication

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class userSignUp : AppCompatActivity() {

    //Declaring private variables for (Auth/Firestore)
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.user_signup)

        // Initialize Firebase services
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Handling the bar padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun saveNewUserButton(view: View) {
        // Getting user inputs from XML fields
        val firstName = findViewById<EditText>(R.id.editTextFirstName).text.toString().trim()
        val lastName = findViewById<EditText>(R.id.editTextLastName).text.toString().trim()
        val age = findViewById<EditText>(R.id.editTextNumberAge).text.toString().trim()
        val address = findViewById<EditText>(R.id.editTextAddress).text.toString().trim()
        val email = findViewById<EditText>(R.id.editTextNewUserEmail).text.toString().trim()
        val password = findViewById<EditText>(R.id.editTextNewUserPassword).text.toString().trim()
        val message = findViewById<TextView>(R.id.textViewMessage)

        // If condition to check for empty fields
        if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() ||
            address.isEmpty() || email.isEmpty() || password.isEmpty()
        ) {
            message.text = "Please fill in all fields!"
            return
        }

        // Creating the user info in Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid

                    // Create the required user data in firestore in order to store it
                    val userData = hashMapOf(
                        "FirstName" to firstName,
                        "LastName" to lastName,
                        "Age" to age,
                        "Address" to address,
                        "Email" to email
                    )

                    if (userId != null) {
                        firestore.collection("users").document(userId)
                            .set(userData)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Successfully created Account", Toast.LENGTH_SHORT).show()
                                message.text = ""
                                finish() // Method for returning to login page
                            }
                            .addOnFailureListener { e ->
                                message.text = "Error while saving user data: ${e.message}"
                            }
                    }
                } else {
                    message.text = "Error: ${task.exception?.message}"
                }
            }
    }
}

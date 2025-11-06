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

class userSignUp : AppCompatActivity() {

    // Declare FirebaseAuth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.user_signup)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Handle system bar padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun saveNewUserButton(view: View) {

        // Get user input from XML fields
        val email = findViewById<EditText>(R.id.editTextNewUserEmail).text.toString().trim()
        val password = findViewById<EditText>(R.id.editTextNewUserPassword).text.toString().trim()
        val message = findViewById<TextView>(R.id.textViewMessage)

        // Check for empty fields
        if (email.isEmpty() || password.isEmpty()) {
            message.text = "Please enter email and password!"
            return
        }

        // Create a new user in Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Success — user created
                    Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                    message.text = ""
                    finish() // Go back to login page
                } else {
                    // Failure — show error message
                    message.text = "Error: ${task.exception?.message}"
                }
            }
    }
}

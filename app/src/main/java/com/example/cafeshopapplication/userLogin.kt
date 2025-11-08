package com.example.cafeshopapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class userLogin : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var messageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_login)

        auth = FirebaseAuth.getInstance()


        emailEditText = findViewById(R.id.editTextUserName)
        passwordEditText = findViewById(R.id.editTextPassword)
        messageTextView = findViewById(R.id.textViewMessage)
    }

   /** fun signUpButton(view: View) {
        val intent = Intent(this, userSignUp::class.java)
        startActivity(intent)
    }**/


    fun registerButton(view: View) {
        // This code will run when the "New User" button is clicked.
        // It creates an intent to open the userSignUp activity.
        val intent = Intent(this, userSignUp::class.java)
        startActivity(intent)
    }

    fun loginButton(view: View) {
        // FIX 2: Use the properties initialized in onCreate instead of calling findViewById again.
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    messageTextView.text = ""
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                    // FIX 3: Redirect to the main application screen (e.g., MainActivity).
                    // IMPORTANT: You must have a MainActivity or change this to your app's actual main screen.
                    val intent = Intent(this, menuPage::class.java)
                    startActivity(intent)
                    finish() // Close the login screen so the user can't go back to it
                } else {
                    // This is where the error in your screenshot is being set.
                    messageTextView.text = "Login failed: ${task.exception?.message}"
                }
            }
    }
}
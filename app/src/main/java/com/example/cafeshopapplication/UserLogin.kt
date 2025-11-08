package com.example.cafeshopapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels // Import the new delegate
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeshopapplication.databinding.UserLoginBinding // Import the auto-generated View Binding class
import com.example.cafeshopapplication.ui.auth.AuthResult
import com.example.cafeshopapplication.ui.auth.AuthViewModel

class UserLogin : AppCompatActivity() {

    // 1. Set up View Binding
    private lateinit var binding: UserLoginBinding

    // 2. Get a reference to our AuthViewModel
    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 3. Inflate the layout using View Binding
        binding = UserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // We removed all the old findViewById code

        // 4. Set click listeners for our buttons
        binding.buttonLoginIn.setOnClickListener {
            val email = binding.editTextUserName.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            // 5. Tell the ViewModel to do the work
            authViewModel.login(email, password)
        }

        binding.buttonRegister.setOnClickListener {
            // This logic stays the same
            val intent = Intent(this, userSignUp::class.java)
            startActivity(intent)
        }

        // 6. Start listening for the result from the ViewModel
        observeAuthResult()
    }

    /**
     * This function "observes" the LiveData in our ViewModel.
     * When the ViewModel sends a result, this code runs.
     */
    private fun observeAuthResult() {
        authViewModel.authResult.observe(this) { result ->
            // 7. The ViewModel sent a result. Check what it is.
            when (result) {
                is AuthResult.Success -> {
                    // It was a success!
                    binding.textViewMessage.text = "" // Clear any old errors
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                    // Navigate to the main menu page
                    val intent = Intent(this, menuPage::class.java)
                    startActivity(intent)
                    finish() // Close the login screen
                }
                is AuthResult.Error -> {
                    // It failed. Show the error message from the ViewModel.
                    binding.textViewMessage.text = result.message
                }
            }
        }
    }

    // REMOVED: The old loginButton(view: View) and registerButton(view: View) functions are gone.
}
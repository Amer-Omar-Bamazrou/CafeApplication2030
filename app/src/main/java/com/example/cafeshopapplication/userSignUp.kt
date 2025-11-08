package com.example.cafeshopapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels // Import the new delegate
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeshopapplication.databinding.UserSignupBinding // Import the auto-generated View Binding class
import com.example.cafeshopapplication.ui.auth.AuthResult
import com.example.cafeshopapplication.ui.auth.AuthViewModel

class userSignUp : AppCompatActivity() {

    // 1. Set up View Binding
    private lateinit var binding: UserSignupBinding

    // 2. Get a reference to our AuthViewModel
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 3. Inflate the layout using View Binding
        binding = UserSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // We removed all the old Firebase and findViewById code

        // 4. Set the click listener for the save button
        binding.buttonSave.setOnClickListener {
            // 5. Get text from our NEW layout fields
            val fullName = binding.editTextFullName.text.toString().trim()
            val phone = binding.editTextPhone.text.toString().trim()
            val address = binding.editTextAddress.text.toString().trim()
            val email = binding.editTextNewUserEmail.text.toString().trim()
            val password = binding.editTextNewUserPassword.text.toString().trim()

            // 6. Tell the ViewModel to do the work!
            authViewModel.register(email, password, fullName, address, phone)
        }

        // 7. Start listening for the result from the ViewModel
        observeRegistrationResult()
    }

    /**
     * This function "observes" the LiveData in our ViewModel.
     * When the ViewModel sends a result, this code runs.
     */
    private fun observeRegistrationResult() {
        authViewModel.authResult.observe(this) { result ->
            // 8. The ViewModel sent a result. Check what it is.
            when (result) {
                is AuthResult.Success -> {
                    // It was a success!
                    Toast.makeText(this, "Successfully created Account", Toast.LENGTH_SHORT).show()
                    binding.textViewMessage.text = "" // Clear any old errors
                    finish() // Close the register page and go back to login
                }
                is AuthResult.Error -> {
                    // It failed. Show the error message from the ViewModel.
                    binding.textViewMessage.text = result.message
                }
            }
        }
    }

    // REMOVED: The old saveNewUserButton(view: View) function is gone.
}
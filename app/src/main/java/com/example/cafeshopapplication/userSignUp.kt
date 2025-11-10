package com.example.cafeshopapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeshopapplication.databinding.UserSignupBinding
import com.example.cafeshopapplication.ui.auth.AuthResult
import com.example.cafeshopapplication.ui.auth.AuthViewModel

class userSignUp : AppCompatActivity() {
    private lateinit var binding: UserSignupBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = UserSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener {
            // get text fields
            val fullName = binding.editTextFullName.text.toString().trim()
            val phone = binding.editTextPhone.text.toString().trim()
            val address = binding.editTextAddress.text.toString().trim()
            val email = binding.editTextNewUserEmail.text.toString().trim()
            val password = binding.editTextNewUserPassword.text.toString().trim()

            authViewModel.register(email, password, fullName, address, phone)
        }

        // 7. Start listening
        observeRegistrationResult()
    }

    /**
     * This function "observes" the LiveData in our ViewModel.
     * When the ViewModel sends a result, this code runs.
     */
    private fun observeRegistrationResult() {
        authViewModel.authResult.observe(this) { result ->
            when (result) {
                is AuthResult.Success -> {
                    Toast.makeText(this, "Successfully created Account", Toast.LENGTH_SHORT).show()
                    binding.textViewMessage.text = "" // Clear any old errors
                    finish()
                }
                is AuthResult.Error -> {
                    binding.textViewMessage.text = result.message
                }
            }
        }
    }
}
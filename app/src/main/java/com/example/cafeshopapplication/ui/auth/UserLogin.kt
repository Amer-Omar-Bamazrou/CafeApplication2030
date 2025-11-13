package com.example.cafeshopapplication.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeshopapplication.MainActivity // <-- IMPORT MAIN ACTIVITY
import com.example.cafeshopapplication.databinding.UserLoginBinding
import com.example.cafeshopapplication.ui.admin.AdminDashboardActivity

class UserLogin : AppCompatActivity() {

    private lateinit var binding: UserLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoginIn.setOnClickListener {
            val email = binding.editTextUserName.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            authViewModel.login(email, password)
        }

        observeAuthResult()
    }

    private fun observeAuthResult() {
        authViewModel.authResult.observe(this) { result ->
            when (result) {
                is AuthResult.Success -> {
                    binding.textViewMessage.text = ""
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                    val intent: Intent
                    if (result.isAdmin) {
                        intent = Intent(this, AdminDashboardActivity::class.java)
                    } else {
                        intent = Intent(this, MainActivity::class.java)
                    }

                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                is AuthResult.Error -> {
                    binding.textViewMessage.text = result.message
                }
            }
        }
    }
}
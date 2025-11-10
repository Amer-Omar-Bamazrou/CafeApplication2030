package com.example.cafeshopapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeshopapplication.databinding.UserLoginBinding
import com.example.cafeshopapplication.ui.auth.AuthResult
import com.example.cafeshopapplication.ui.auth.AuthViewModel
import com.example.cafeshopapplication.ui.menu.MenuPage

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

        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this, userSignUp::class.java)
            startActivity(intent)
        }
        observeAuthResult()
    }

    private fun observeAuthResult() {
        authViewModel.authResult.observe(this) { result ->
            when (result) {
                is AuthResult.Success -> {
                    binding.textViewMessage.text = ""
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                    //Re-directing to new page
                    val intent = Intent(this, MenuPage::class.java)
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
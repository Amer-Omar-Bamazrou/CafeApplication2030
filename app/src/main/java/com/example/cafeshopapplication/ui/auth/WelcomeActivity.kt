package com.example.cafeshopapplication.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeshopapplication.databinding.ActivityWelcomeBinding
import com.bumptech.glide.Glide

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding


    private val WELCOME_IMAGE_URL = "https://i.pinimg.com/736x/af/74/e9/af74e9d2739e7fd7890db43e55ba5cde.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
            .load(WELCOME_IMAGE_URL)
            .centerCrop()
            .into(binding.imageViewWelcome)

        binding.buttonWelcomeSignIn.setOnClickListener {
            val intent = Intent(this, UserLogin::class.java)
            startActivity(intent)
        }

        binding.buttonWelcomeSignUp.setOnClickListener {
            val intent = Intent(this, userSignUp::class.java)
            startActivity(intent)
        }
    }
}
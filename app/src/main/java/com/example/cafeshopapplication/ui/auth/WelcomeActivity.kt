package com.example.cafeshopapplication.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cafeshopapplication.R
import com.example.cafeshopapplication.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    private val WELCOME_IMAGE_URL = "https://images.unsplash.com/photo-1559925393-8be0ec476ac8"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load background image
        Glide.with(this)
            .load(WELCOME_IMAGE_URL)
            .centerCrop()
            .placeholder(R.drawable.coffee_welcome)
            .error(R.drawable.coffee_welcome)
            .into(binding.imageViewWelcome)

        // Load animations
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)

        // Apply animations
        binding.imageViewWelcome.startAnimation(fadeIn)
        binding.textWelcome.startAnimation(slideUp)
        binding.textSubtitle.startAnimation(fadeIn)
        binding.buttonWelcomeSignIn.startAnimation(fadeIn)
        binding.buttonWelcomeSignUp.startAnimation(fadeIn)

        // Button clicks
        binding.buttonWelcomeSignIn.setOnClickListener {
            startActivity(Intent(this, UserLogin::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        binding.buttonWelcomeSignUp.setOnClickListener {
            startActivity(Intent(this, userSignUp::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}

package com.example.cafeshopapplication.ui.admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeshopapplication.databinding.AdminDashboardActivityBinding
import com.example.cafeshopapplication.ui.feedback.FeedbackActivity

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var binding: AdminDashboardActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminDashboardActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Manage Menu
        binding.cardManageMenu.setOnClickListener {
            val intent = Intent(this, AdminManageMenuActivity::class.java)
            startActivity(intent)
        }

        // View Orders
        binding.cardViewOrders.setOnClickListener {
            val intent = Intent(this, AdminViewOrdersActivity::class.java)
            startActivity(intent)
        }

        binding.cardViewFeedback.setOnClickListener {
            val intent = Intent(this, AdminViewFeedbackActivity::class.java)
            startActivity(intent)
        }

    }
}

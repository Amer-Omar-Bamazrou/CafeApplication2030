package com.example.cafeshopapplication.ui.admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cafeshopapplication.databinding.AdminDashboardActivityBinding

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var binding: AdminDashboardActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminDashboardActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonManageMenu.setOnClickListener {
            val intent = Intent(this, AdminManageMenuActivity::class.java)
            startActivity(intent)
        }


        binding.buttonViewOrders.setOnClickListener {
            val intent = Intent(this, AdminViewOrdersActivity::class.java)
            startActivity(intent)
        }
    }
}
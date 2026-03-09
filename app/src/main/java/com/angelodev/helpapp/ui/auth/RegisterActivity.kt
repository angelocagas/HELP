package com.angelodev.helpapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.angelodev.helpapp.R
import com.angelodev.helpapp.databinding.ActivityRegisterBinding
import com.angelodev.helpapp.ui.home.MenuActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        if (viewModel.isLoggedIn) {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
            return
        }

        binding.phone.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val currentText = binding.phone.text.toString()
                if (currentText.isEmpty() || !currentText.startsWith("+639")) {
                    binding.phone.setText("+639")
                }
            }
        }

        binding.login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.submit.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val name = binding.name.text.toString().trim()
            val phone = binding.phone.text.toString().trim()

            if (!isValidEmail(email)) {
                binding.email.error = "You must enter valid email"
                return@setOnClickListener
            }
            if (password.length < 8) {
                binding.password.error = "Password must have 8 characters"
                return@setOnClickListener
            }
            if (phone.length != 13) {
                binding.phone.error = "ex. +639123456789"
                return@setOnClickListener
            }
            if (name.length < 4) {
                binding.name.error = "Name must have 4 characters"
                return@setOnClickListener
            }
            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
                binding.error.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.error.visibility = View.GONE
                }, 3000)
                return@setOnClickListener
            }

            viewModel.register(name, phone, email, password)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.registerResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            }.onFailure {
                Toast.makeText(this, "Registration Failed. Try another email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

package com.sa.organicStore.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.sa.organicStore.database.databaseInstance.AppDatabase
import com.sa.organicStore.database.dao.UserDAO
import com.sa.organicStore.database.entities.UserEntity
import com.sa.organicStore.databinding.ActivityLoginBinding
import com.sa.organicStore.utils.UserPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userDao: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = AppDatabase.invoke(applicationContext)
        userDao = database.getUserDAO()

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.tvSignup.setOnClickListener {
            navigateToSignupActivity()
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text?.toString()
            val password = binding.etPassword.text?.toString()
            loginUser(email, password)
        }
    }

    private fun navigateToSignupActivity() {
        startActivity(Intent(this, SignUpActivity::class.java))
        finish()
    }

    private fun loginUser(email: String?, password: String?) {
        if (email.isNullOrEmpty() && password.isNullOrEmpty()) {
            showToast("Empty email and password!")
        } else if (email.isNullOrEmpty()) {
            showToast("Empty email")
        } else if (password.isNullOrEmpty()) {
            showToast("Empty password")
        } else if (!isValidEmail(email)) {
            showToast("Invalid email format!")
        } else {
            lifecycleScope.launch {
                val userData = userDao.getUserByEmailAndPassword(email, password)
                if (userData == null) {
                    withContext(Dispatchers.Main) {
                        showToast("User not found")
                    }
                } else if (email != userData.email) {
                    showToast("Wrong email")
                } else if (password != userData.password) {
                    showToast("Wrong password")
                } else {
                    Log.d("USER", "fun loginUser: userId = ${userData.userId}")
                    UserPrefs(this@LoginActivity).setUser(userData)
                    withContext(Dispatchers.Main) {
                        navigateToHomeActivity()
                    }
                }
            }
        }
    }

    private fun isValidEmail(email: String?): Boolean {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun saveUserInPref(newUser: UserEntity) {
        Log.d("USER", "fun saveUserInPref: userId= ${newUser.userId}")
    }

    private fun navigateToHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()

    }
}
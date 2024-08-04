package com.sa.organicStore.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.sa.organicStore.database.dao.UserDAO
import com.sa.organicStore.database.databaseInstance.AppDatabase
import com.sa.organicStore.database.entities.UserEntity
import com.sa.organicStore.databinding.ActivitySignUpBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var userDao: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDatabase = AppDatabase.invoke(applicationContext)
        userDao = userDatabase.getUserDAO()

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.ivSubmit.setOnClickListener {
            val name = binding.tvName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "Name cannot be empty!", Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(email)) {
                Toast.makeText(this, "Invalid email format!", Toast.LENGTH_SHORT).show()
            } else if (!isValidPassword(password)) {
                Toast.makeText(this, "Password must be at least 8 characters long and include a number, an uppercase letter, and a special character!", Toast.LENGTH_LONG).show()
            } else {
                saveUserInDatabase(name, email, password)
            }
        }

        binding.tvLogin.setOnClickListener {
            navigateToLoginActivity()
        }
    }

    private fun isValidEmail(email: String?): Boolean {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String?): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*@#$%^&+=]).{8,}$"
        return password != null && password.matches(passwordPattern.toRegex())
    }

    private fun saveUserInDatabase(name: String, email: String, password: String) {
        lifecycleScope.launch {
            val user = userDao.getUserByEmail(email = email)
            if (user == null) {
                val newUser = UserEntity(name = name, email = email, password = password)
                userDao.insertUser(newUser)
                saveLoginState(newUser)
                navigateToHomeActivity()
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SignUpActivity, "User already exists with this email.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveLoginState(newUser: UserEntity) {
        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val json: String = Gson().toJson(newUser)
        with(sharedPref.edit()) {
            putString("user", json)
            apply()
        }
    }

    private fun navigateToHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun navigateToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
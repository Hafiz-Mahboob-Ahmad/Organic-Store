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
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(this, "Empty email or password!", Toast.LENGTH_SHORT).show()
        }
//        else if (!isValidEmail(email)) {
//            Toast.makeText(this, "Invalid email format!", Toast.LENGTH_SHORT).show()
//        }
        else {
            lifecycleScope.launch {
                val user = userDao.getUserByEmailAndPassword(email, password)
                if (user != null) {
                    Log.d("USER", "fun loginUser: userId = ${user.userId}")
                    saveUserInPref(user)
                    withContext(Dispatchers.Main) {
                        navigateToHomeActivity()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "User not found", Toast.LENGTH_SHORT).show()
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
        UserPrefs(this@LoginActivity).setUser(newUser)
    }

    private fun navigateToHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
package com.sa.organicStore.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.sa.organicStore.R
import com.sa.organicStore.database.loginSignup.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            if (isUserLoggedIn() == null) {
                startActivity(Intent(this@SplashScreenActivity, WelcomeActivity::class.java))

            } else {
                startActivity(Intent(this@SplashScreenActivity, HomeActivity::class.java))
            }
            finish()
        }
    }

    private fun isUserLoggedIn(): UserEntity? {
        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val json = sharedPref.getString("user", null)
        return if (json == null) {
            null
        } else {
            Gson().fromJson(json, UserEntity::class.java)
        }
    }
}

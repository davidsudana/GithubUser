package com.david.githubmobile.ui.view.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.david.githubmobile.databinding.ActivitySplashScreenBinding
import com.david.githubmobile.ui.view.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val handler = Handler(mainLooper)

        handler.postDelayed({
            viewModel.getTheme().observe(this@SplashScreenActivity) { isDarkMode ->
                if (isDarkMode) {
                    redirect()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    redirect()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }, 3000L)
    }

    private fun redirect() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}
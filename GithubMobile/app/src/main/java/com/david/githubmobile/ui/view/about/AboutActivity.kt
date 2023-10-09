package com.david.githubmobile.ui.view.about

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.david.githubmobile.R
import com.david.githubmobile.databinding.ActivityAboutBinding
import com.david.githubmobile.utils.Constant
import com.david.githubmobile.utils.Constant.ABOUT_AVATAR_URL

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.about)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        Glide.with(applicationContext)
            .load(ABOUT_AVATAR_URL)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivAvatar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.tv_github -> {
                val url = "https://github.com/davidsudana"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
            R.id.tv_linkedin -> {
                val url = "https://www.linkedin.com/in/david-sudana/"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
            R.id.iv_avatar -> {
                val url = "@drawable/profil"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)

            }
        }
    }
}
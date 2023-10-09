package com.david.githubmobile.ui.view.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.david.githubmobile.R
import com.david.githubmobile.data.Result
import com.david.githubmobile.data.local.entity.UserEntity
import com.david.githubmobile.databinding.ActivityDetailBinding
import com.david.githubmobile.ui.adapter.DetailAdapter
import com.david.githubmobile.utils.Constant.EXTRA_USER
import com.david.githubmobile.utils.Constant.TAB_TITLES
import com.david.githubmobile.utils.StateApp
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity(), StateApp<UserEntity?> {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USER)
        val pageAdapter = DetailAdapter(this, username.toString())

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getUser(username.toString()).observe(this@DetailActivity) {
                when (it) {
                    is Result.Error -> onFailed(it.message)
                    is Result.Loading -> onLoading()
                    is Result.Success -> onSuccess(it.data)
                }
            }
        }

        binding.apply {
            viewPager.adapter = pageAdapter
            TabLayoutMediator(tabs, viewPager) { tabs, position ->
                tabs.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_action_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share_action -> {
                val username = intent.getStringExtra(EXTRA_USER)
                val userUrl = "https://github.com/$username"
                val share = Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT, userUrl)
                startActivity(Intent.createChooser(share, "share"))
                true
            }
            else -> false
        }
    }

    override fun onLoading() {
        binding.apply {
            fabFavorite.visibility = visible
            progressBar.visibility = visible
        }
    }

    override fun onSuccess(data: UserEntity?) {
        binding.apply {
            progressBar.visibility = gone
            supportActionBar?.title = data?.username

            Glide.with(applicationContext)
                .load(data?.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(ivAvatar)

            tvName.text = data?.name
            tvUsername.text = data?.username
            tvCompany.text = data?.company ?: getString(R.string.company_null)
            tvLocation.text = data?.location ?: getString(R.string.location_null)
            tvTotalRepository.text = data?.repository.toString()
            tvTotalFollowers.text = data?.follower.toString()
            tvTotalFollowing.text = data?.following.toString()

            if (data?.isFavorite == true) {
                fabFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                fabFavorite.setImageResource(R.drawable.ic_favorite_border)
            }

            fabFavorite.setOnClickListener {
                if (data?.isFavorite == true) {
                    data.isFavorite = false
                    viewModel.deleteFromFavorite(data)
                    fabFavorite.setImageResource(R.drawable.ic_favorite_border)
                    Toast.makeText(
                        baseContext,
                        getString(R.string.removed_favorite),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    data?.isFavorite = true
                    data?.let { user -> viewModel.addToFavorite(user) }
                    fabFavorite.setImageResource(R.drawable.ic_favorite)
                    Toast.makeText(
                        baseContext,
                        getString(R.string.added_favorite),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onFailed(message: String?) {
        binding.apply {
            fabFavorite.visibility = gone
            progressBar.visibility = gone
        }
    }
}
package com.david.githubmobile.ui.view.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.githubmobile.R
import com.david.githubmobile.data.Result
import com.david.githubmobile.data.local.entity.UserEntity
import com.david.githubmobile.databinding.ActivityFavoriteBinding
import com.david.githubmobile.ui.adapter.UserAdapter
import com.david.githubmobile.utils.StateApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity(), StateApp<List<UserEntity>> {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var userAdapter: UserAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorites_title)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        userAdapter = UserAdapter()

        binding.rvFavorite.apply {
            adapter = userAdapter
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavorites().observe(this@FavoriteActivity) {
                when (it) {
                    is Result.Loading -> onLoading()
                    is Result.Success -> it.data?.let { user -> onSuccess(user) }
                    is Result.Error -> onFailed(it.message)
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavorites().observe(this@FavoriteActivity) {
                when (it) {
                    is Result.Loading -> onLoading()
                    is Result.Success -> it.data?.let { user -> onSuccess(user) }
                    is Result.Error -> onFailed(it.message)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onLoading() {
        binding.apply {
            progressBar.visibility = visible
            ivNoFavoriteUser.visibility = gone
        }
    }

    override fun onSuccess(data: List<UserEntity>) {
        binding.apply {
            progressBar.visibility = gone
            ivNoFavoriteUser.visibility = gone
        }
        userAdapter.setAllData(data)
    }

    override fun onFailed(message: String?) {
        if (message == null) {
            binding.apply {
                progressBar.visibility = gone
                ivNoFavoriteUser.visibility = visible
                tvFavoriteError.text = getString(R.string.no_favorites_user)
                rvFavorite.visibility = gone
            }
        }
    }
}
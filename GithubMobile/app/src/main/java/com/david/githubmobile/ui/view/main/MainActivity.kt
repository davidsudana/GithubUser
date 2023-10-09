package com.david.githubmobile.ui.view.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.githubmobile.R
import com.david.githubmobile.data.Result
import com.david.githubmobile.data.local.entity.UserEntity
import com.david.githubmobile.databinding.ActivityMainBinding
import com.david.githubmobile.ui.adapter.UserAdapter
import com.david.githubmobile.ui.view.about.AboutActivity
import com.david.githubmobile.ui.view.favorite.FavoriteActivity
import com.david.githubmobile.ui.view.setting.SettingActivity
import com.david.githubmobile.utils.StateApp
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), StateApp<List<UserEntity>> {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        binding.initSearch.rvListUser.apply {
            adapter = userAdapter
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_action_items, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_app_bar).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchUser(query).observe(this@MainActivity) { a ->
                    when (a) {
                        is Result.Loading -> onLoading()
                        is Result.Success -> a.data?.let { b -> onSuccess(b) }
                        is Result.Error -> onFailed(a.message)
                    }
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean = false
        })
        searchView.setOnCloseListener {
            binding.initSearch.rvListUser.visibility = gone
            true
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite_action -> {
                val intent = Intent(applicationContext, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.settings_action -> {
                val intent = Intent(applicationContext, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.about_action -> {
                val intent = Intent(applicationContext, AboutActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.exit_action -> {
                this@MainActivity.finish()
                exitProcess(0)
            }
            else -> false
        }
    }

    override fun onLoading() {
        binding.initSearch.apply {
            ivSearchIcon.visibility = gone
            tvMessage.visibility = gone
            progressBar.visibility = visible
            rvListUser.visibility = gone
        }
    }

    override fun onSuccess(data: List<UserEntity>) {
        userAdapter.setAllData(data)
        binding.initSearch.apply {
            ivSearchIcon.visibility = gone
            tvMessage.visibility = gone
            progressBar.visibility = gone
            rvListUser.visibility = visible
        }
    }

    override fun onFailed(message: String?) {
        binding.initSearch.apply {
            if (message == null) {
                ivSearchIcon.apply {
                    setImageResource(R.drawable.ic_not_found)
                    visibility = visible
                }
                tvMessage.apply {
                    text = resources.getString(R.string.user_not_found)
                    visibility = visible
                }
            } else {
                ivSearchIcon.apply {
                    setImageResource(R.drawable.ic_not_found)
                    visibility = visible
                }
                tvMessage.apply {
                    text = resources.getString(R.string.no_internet)
                    visibility = visible
                }
            }
            progressBar.visibility = gone
            rvListUser.visibility = gone
        }
    }
}
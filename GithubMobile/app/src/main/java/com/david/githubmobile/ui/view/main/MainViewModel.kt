package com.david.githubmobile.ui.view.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.david.githubmobile.data.UserRepository

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val userRepository = UserRepository(application)

    fun searchUser(query: String) = userRepository.searchUser(query)
}
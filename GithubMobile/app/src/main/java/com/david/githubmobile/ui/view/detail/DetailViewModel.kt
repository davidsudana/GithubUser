package com.david.githubmobile.ui.view.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.david.githubmobile.data.UserRepository
import com.david.githubmobile.data.local.entity.UserEntity
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)

    suspend fun getUser(username: String) = userRepository.getUser(username)

    fun addToFavorite(user: UserEntity) = viewModelScope.launch {
        userRepository.addToFavorite(user)
    }

    fun deleteFromFavorite(user: UserEntity) = viewModelScope.launch {
        userRepository.deleteFromFavorite(user)
    }

    fun getFollowers(username: String) = userRepository.getFollowers(username)

    fun getFollowing(username: String) = userRepository.getFollowing(username)
}
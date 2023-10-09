package com.david.githubmobile.ui.view.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.david.githubmobile.data.UserRepository

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private val userRepository = UserRepository(application)

    suspend fun getFavorites() = userRepository.getFavorites()
}
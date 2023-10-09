package com.david.githubmobile.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.david.githubmobile.R

object Constant {
    const val BASE_URL = "https://api.github.com"
    const val GITHUB_TOKEN = "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"
    const val EXTRA_USER = "USER"
    const val EXTRA_USERNAME = "USERNAME"
    const val USER_DATASTORE = "USER_DATASTORE"
    const val ABOUT_AVATAR_URL = "https://media-exp1.licdn.com/dms/image/C5603AQGbmaWlh0qWNw/profile-displayphoto-shrink_200_200/0/1645405001028?e=1652313600&v=beta&t=0Bao5zZzQY99G8wmvOicgXpMmHgzNf3Oorx9YT1tLrs"
    val THEME_KEY = booleanPreferencesKey("THEME_SETTING")
    val TAB_TITLES = intArrayOf(
        R.string.followers,
        R.string.following
    )
}
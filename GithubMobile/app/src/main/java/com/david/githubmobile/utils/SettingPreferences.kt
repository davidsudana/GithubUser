package com.david.githubmobile.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.david.githubmobile.utils.Constant.THEME_KEY
import com.david.githubmobile.utils.Constant.USER_DATASTORE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences(private val context: Context) {
    private val Context.userPreferenceDataStore: DataStore<Preferences> by preferencesDataStore(
        name = USER_DATASTORE
    )

    suspend fun setTheme(isDarkModeActive: Boolean) {
        context.userPreferenceDataStore.edit {
            it[THEME_KEY] = isDarkModeActive
        }
    }

    fun getTheme(): Flow<Boolean> =
        context.userPreferenceDataStore.data.map {
            it[THEME_KEY] ?: false
        }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: SettingPreferences? = null

        fun getInstance(context: Context): SettingPreferences =
            instance ?: synchronized(this) {
                val newInstance = instance ?: SettingPreferences(context).also { instance = it }
                newInstance
            }
    }
}
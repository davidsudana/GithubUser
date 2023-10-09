package com.david.githubmobile.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.david.githubmobile.data.local.entity.UserEntity
import com.david.githubmobile.data.local.room.UserDao
import com.david.githubmobile.data.local.room.UserDatabase
import com.david.githubmobile.data.remote.response.SearchResponse
import com.david.githubmobile.data.remote.retrofit.ApiConfig
import com.david.githubmobile.data.remote.retrofit.ApiService
import com.david.githubmobile.utils.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(application: Application) {
    private val apiService: ApiService = ApiConfig.create()
    private val userDao: UserDao
    private val settingPref: SettingPreferences

    init {
        val database: UserDatabase = UserDatabase.getInstance(application)
        userDao = database.userDao()
        settingPref = SettingPreferences.getInstance(application)
    }

    fun searchUser(query: String): LiveData<Result<List<UserEntity>>> {
        val listUser = MutableLiveData<Result<List<UserEntity>>>()
        listUser.postValue(Result.Loading())

        apiService.searchUsers(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val list = response.body()?.items

                if (list.isNullOrEmpty()) {
                    listUser.postValue(Result.Error(null))
                } else {
                    listUser.postValue(Result.Success(list))
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                listUser.postValue(Result.Error(t.message))
            }
        })

        return listUser
    }

    suspend fun getUser(username: String): LiveData<Result<UserEntity>> {
        val user = MutableLiveData<Result<UserEntity>>()

        if (userDao.isFavorite(username) != null) {
            user.postValue(Result.Success(userDao.isFavorite(username)))
        } else {
            apiService.getUser(username).enqueue(object : Callback<UserEntity> {
                override fun onResponse(call: Call<UserEntity>, response: Response<UserEntity>) {
                    val result = response.body()
                    user.postValue(Result.Success(result))
                }

                override fun onFailure(call: Call<UserEntity>, t: Throwable) {
                    user.postValue(Result.Error(t.message))
                }
            })
        }

        return user
    }

    fun getFollowers(username: String): LiveData<Result<List<UserEntity>>> {
        val listUser = MutableLiveData<Result<List<UserEntity>>>()
        listUser.postValue(Result.Loading())

        apiService.getFollowers(username).enqueue(object : Callback<List<UserEntity>> {
            override fun onResponse(
                call: Call<List<UserEntity>>,
                response: Response<List<UserEntity>>
            ) {
                val list = response.body()

                if (list.isNullOrEmpty()) {
                    listUser.postValue(Result.Error(null))
                } else {
                    listUser.postValue(Result.Success(list))
                }
            }

            override fun onFailure(call: Call<List<UserEntity>>, t: Throwable) {
                listUser.postValue(Result.Error(t.message))
            }
        })

        return listUser
    }

    fun getFollowing(username: String): LiveData<Result<List<UserEntity>>> {
        val listUser = MutableLiveData<Result<List<UserEntity>>>()
        listUser.postValue(Result.Loading())

        apiService.getFollowing(username).enqueue(object : Callback<List<UserEntity>> {
            override fun onResponse(
                call: Call<List<UserEntity>>,
                response: Response<List<UserEntity>>
            ) {
                val list = response.body()

                if (list.isNullOrEmpty()) {
                    listUser.postValue(Result.Error(null))
                } else {
                    listUser.postValue(Result.Success(list))
                }
            }

            override fun onFailure(call: Call<List<UserEntity>>, t: Throwable) {
                listUser.postValue(Result.Error(t.message))
            }
        })

        return listUser
    }

    suspend fun getFavorites(): LiveData<Result<List<UserEntity>>> {
        val listFavorite = MutableLiveData<Result<List<UserEntity>>>()
        listFavorite.postValue(Result.Loading())

        if (userDao.getFavorites().isNullOrEmpty()) {
            listFavorite.postValue(Result.Error(null))
        } else {
            listFavorite.postValue(Result.Success(userDao.getFavorites()))
        }

        return listFavorite
    }

    suspend fun addToFavorite(user: UserEntity) = userDao.addToFavorite(user)

    suspend fun deleteFromFavorite(user: UserEntity) = userDao.deleteFromFavorite(user)

    suspend fun setTheme(isDarkMode: Boolean) = settingPref.setTheme(isDarkMode)

    fun getTheme() = settingPref.getTheme()
}
package com.david.githubmobile.data.remote.retrofit

import com.david.githubmobile.data.local.entity.UserEntity
import com.david.githubmobile.data.remote.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<SearchResponse>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Call<UserEntity>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<UserEntity>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<UserEntity>>
}
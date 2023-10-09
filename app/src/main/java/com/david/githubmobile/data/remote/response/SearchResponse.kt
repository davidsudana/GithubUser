package com.david.githubmobile.data.remote.response

import com.david.githubmobile.data.local.entity.UserEntity
import com.squareup.moshi.Json

data class SearchResponse(
    @field:Json(name = "items") val items: List<UserEntity>
)
package com.android.example.demomvvm.data.remote

import com.android.example.demomvvm.data.remote.model.Repo
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoSearchResponse(

    @Json(name = "total_count")
    val total: Int = 0,

    @Json(name = "items")
    val items: List<Repo> = emptyList(),

    val nextPage: Int? = null,
)
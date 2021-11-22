package com.android.example.demomvvm.data.remote.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Repo(

    @Json(name = "id")
    val id: Long,

    @Json(name = "name")
    val name: String,

    @Json(name = "full_name")
    val fullName: String,

    @Json(name = "description")
    val description: String?,

    @Json(name = "html_url")
    val url: String,

    @Json(name = "stargazers_count")
    val stars: Int,

    @Json(name = "forks_count")
    val forks: Int,

    @Json(name = "language")
    val language: String?,

    @Json(name = "watchers")
    val watchers: Int,

    @Json(name = "owner")
    val owner: Owner,

    @Json(name = "created_at")
    val createDate: String,

    @Json(name = "updated_at")
    val updateDate: String,

    @Json(name = "open_issues")
    val openIssues: Int

) : Parcelable
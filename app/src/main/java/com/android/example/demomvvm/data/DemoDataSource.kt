package com.android.example.demomvvm.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.android.example.demomvvm.data.remote.RepoSearchResponse
import com.android.example.demomvvm.data.remote.model.Repo


interface DemoDataSource {



    fun getSearchResults(query: String): LiveData<PagingData<Repo>>

}
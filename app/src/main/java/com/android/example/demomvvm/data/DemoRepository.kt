package com.android.example.demomvvm.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.android.example.demomvvm.data.remote.DemoApiService
import com.android.example.demomvvm.data.remote.GithubPagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import androidx.paging.liveData

class DemoRepository(
    private val service: DemoApiService
) : DemoDataSource {


    override fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(service, query) }
        ).liveData

}


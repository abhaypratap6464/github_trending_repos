package com.android.example.demomvvm.ui.repo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.android.example.demomvvm.core.base.BaseViewModel
import com.android.example.demomvvm.data.DemoDataSource
import com.android.example.demomvvm.data.remote.model.Repo

class RepoViewModel(
    val app: Application,
    private val repository: DemoDataSource,
) : BaseViewModel(app) {


    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val reposList = currentQuery.switchMap { queryString ->
        if (queryString == "" || queryString.equals(null))
            repository.getSearchResults(DEFAULT_QUERY)
                .cachedIn(viewModelScope) else repository.getSearchResults(queryString)
            .cachedIn(viewModelScope)
    }


    fun searchRepos(query: String) {
        hideKeyboard.value = true
        currentQuery.value = query
    }


    companion object {
        private const val DEFAULT_QUERY = "since=monthly"
    }


}

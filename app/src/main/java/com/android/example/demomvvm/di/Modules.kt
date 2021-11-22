package com.android.example.demomvvm.di


import com.android.example.demomvvm.data.DemoDataSource
import com.android.example.demomvvm.data.DemoRepository
import com.android.example.demomvvm.data.remote.DemoApi
import com.android.example.demomvvm.ui.repo.RepoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val apiModule = module {
    single {
        return@single DemoApi.createRetrofitService()
    }
}

val repositoryModule = module {
    single { DemoRepository(get()) as DemoDataSource }
}

val repoViewModel = module {
    viewModel {
        RepoViewModel(
            get(), get(

            )
        )
    }
}






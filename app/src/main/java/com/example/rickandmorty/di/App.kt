package com.example.rickandmorty.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi

@ExperimentalPagingApi
class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().appModule(AppModule(context = this)).build()
    }
}
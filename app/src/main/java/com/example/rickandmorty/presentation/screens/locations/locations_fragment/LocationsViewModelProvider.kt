package com.example.rickandmorty.presentation.screens.locations.locations_fragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.rickandmorty.data.remote.api.RetrofitInstance
import com.example.rickandmorty.data.repositories.locations_repositories.LocationsRepositoryImpl
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.use_cases.locations.locations_usecases.GetAllLocationsUseCase

@ExperimentalPagingApi
class LocationsViewModelProvider(
    context: Context
) : ViewModelProvider.Factory {

    private val retrofitInstance by lazy {
        RetrofitInstance.locationsApi
    }

    private val db by lazy {
        RickAndMortyDatabase(context = context)
    }

    private val locationsRepository by lazy {
        LocationsRepositoryImpl(db = db, locationsApi = retrofitInstance)
    }

    private val getAllLocationsUseCase by lazy {
        GetAllLocationsUseCase(locationsRepository = locationsRepository)
    }


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationsViewModel(
            getAllLocationsUseCase = getAllLocationsUseCase
        ) as T
    }
}
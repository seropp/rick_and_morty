package com.example.rickandmorty.presentation.screens

sealed class ResultFromRoom{
    data class Success<T>(var param: T) : ResultFromRoom()
    data class Error(val exception: Throwable): ResultFromRoom()
}

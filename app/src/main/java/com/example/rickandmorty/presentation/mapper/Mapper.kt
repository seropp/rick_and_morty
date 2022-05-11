package com.example.rickandmorty.presentation.mapper

interface Mapper<T, R> {
    fun transform(data: T): R
}
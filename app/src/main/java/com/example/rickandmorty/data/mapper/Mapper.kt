package com.example.rickandmorty.data.mapper

interface Mapper<T, R> {
    fun transform(data: T): R
}
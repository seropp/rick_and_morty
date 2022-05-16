package com.example.rickandmorty.data.repositories.characters_repositories

import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.repositories.characters_repositories.GetCharacterFiltersRepository
import kotlinx.coroutines.flow.Flow

class GetCharacterFiltersRepositoryImpl(
    private val db: RickAndMortyDatabase
) : GetCharacterFiltersRepository {

    override fun getListCharactersSpecies(): Flow<List<String>> =
        db.getCharacterDao().getSpecies()


    override fun getListCharactersTypes(): Flow<List<String>> =
        db.getCharacterDao().getTypes()

}
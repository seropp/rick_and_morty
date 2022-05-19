package com.example.rickandmorty.data.storage.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmorty.data.models.characters.Characters
import com.example.rickandmorty.data.models.episodes.Episode
import com.example.rickandmorty.data.models.location.Location
import com.example.rickandmorty.data.storage.room.converter.Converter
import com.example.rickandmorty.data.storage.room.dao.CharacterDao
import com.example.rickandmorty.data.storage.room.dao.EpisodeDao
import com.example.rickandmorty.data.storage.room.dao.LocationDao
import com.example.rickandmorty.data.storage.room.dao.page_keys_dao.CharactersKeysDao
import com.example.rickandmorty.data.models.page_keys.CharactersPageKeys
import com.example.rickandmorty.data.models.page_keys.EpisodesPageKeys
import com.example.rickandmorty.data.models.page_keys.LocationsPageKeys
import com.example.rickandmorty.data.storage.room.dao.page_keys_dao.EpisodesKeysDao
import com.example.rickandmorty.data.storage.room.dao.page_keys_dao.LocationsKeysDao

@Database(
    entities = [
        Characters::class,
        Location::class,
        Episode::class,
        CharactersPageKeys::class,
        LocationsPageKeys::class,
        EpisodesPageKeys::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDao
    abstract fun getLocationDao(): LocationDao
    abstract fun getEpisodeDao(): EpisodeDao
    abstract fun getCharactersKeyDao(): CharactersKeysDao
    abstract fun getLocationsKeyDao(): LocationsKeysDao
    abstract fun getEpisodesKeyDao(): EpisodesKeysDao


}
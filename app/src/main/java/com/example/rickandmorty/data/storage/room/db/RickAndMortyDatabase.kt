package com.example.rickandmorty.data.storage.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmorty.data.storage.room.converter.Converter
import com.example.rickandmorty.data.storage.room.dao.CharacterDao
import com.example.rickandmorty.data.storage.room.dao.EpisodeDao
import com.example.rickandmorty.data.storage.room.dao.LocationDao
import com.example.rickandmorty.data.storage.room.dao.PageKeyDao
import com.example.rickandmorty.data.storage.room.entities.PageKeyEntity
import com.example.rickandmorty.data.storage.room.entities.character.CharacterEntity
import com.example.rickandmorty.data.storage.room.entities.episode.EpisodeEntity
import com.example.rickandmorty.data.storage.room.entities.location.LocationEntity

@Database(
    entities = [
        CharacterEntity::class,
        LocationEntity::class,
        EpisodeEntity::class,
        PageKeyEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDao
    abstract fun getLocationDao(): LocationDao
    abstract fun getEpisodeDao(): EpisodeDao
    abstract fun getPageKeyDao(): PageKeyDao


    companion object {
        @Volatile
        private var instance: RickAndMortyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context = context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RickAndMortyDatabase::class.java,
                "ContactsDB.bd"
            ).build()
    }
}
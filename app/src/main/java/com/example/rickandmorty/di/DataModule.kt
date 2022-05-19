package com.example.rickandmorty.di


import android.app.Application
import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.example.rickandmorty.data.remote.api.chatacters.CharacterDetailsApi
import com.example.rickandmorty.data.remote.api.chatacters.CharactersApi
import com.example.rickandmorty.data.remote.api.episodes.EpisodeDetailsApi
import com.example.rickandmorty.data.remote.api.episodes.EpisodesApi
import com.example.rickandmorty.data.remote.api.locations.LocationDetailsApi
import com.example.rickandmorty.data.remote.api.locations.LocationsApi
import com.example.rickandmorty.data.repositories.characters_repositories.CharacterDetailsRepositoryImpl
import com.example.rickandmorty.data.repositories.characters_repositories.CharactersRepositoryImpl
import com.example.rickandmorty.data.repositories.characters_repositories.GetCharacterFiltersRepositoryImpl
import com.example.rickandmorty.data.repositories.episodes_repositories.EpisodeDetailsRepositoryImpl
import com.example.rickandmorty.data.repositories.episodes_repositories.EpisodesRepositoryImpl
import com.example.rickandmorty.data.repositories.episodes_repositories.GetEpisodeFiltersRepositoryImpl
import com.example.rickandmorty.data.repositories.locations_repositories.GetLocationFiltersRepositoryImpl
import com.example.rickandmorty.data.repositories.locations_repositories.LocationDetailsRepositoryImpl
import com.example.rickandmorty.data.repositories.locations_repositories.LocationsRepositoryImpl
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.repositories.characters_repositories.CharacterDetailsRepository
import com.example.rickandmorty.domain.repositories.characters_repositories.CharactersRepository
import com.example.rickandmorty.domain.repositories.characters_repositories.GetCharacterFiltersRepository
import com.example.rickandmorty.domain.repositories.episodes_repositories.EpisodeDetailsRepository
import com.example.rickandmorty.domain.repositories.episodes_repositories.EpisodesRepository
import com.example.rickandmorty.domain.repositories.episodes_repositories.GetEpisodeFiltersRepository
import com.example.rickandmorty.domain.repositories.locations_repositories.GetLocationFiltersRepository
import com.example.rickandmorty.domain.repositories.locations_repositories.LocationDetailsRepository
import com.example.rickandmorty.domain.repositories.locations_repositories.LocationsRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideLocationsRepository(
        locationsApi: LocationsApi,
        db: RickAndMortyDatabase
    ): LocationsRepository {
        return LocationsRepositoryImpl(
            locationsApi = locationsApi,
            db = db
        )
    }

    @Provides
    @Singleton
    fun provideLocationDetailsRepository(
        locationDetailsApi: LocationDetailsApi,
        db: RickAndMortyDatabase
    ): LocationDetailsRepository {
        return LocationDetailsRepositoryImpl(
            locationDetailsApi = locationDetailsApi,
            db = db
        )
    }

    @Provides
    @Singleton
    fun provideGetLocationFiltersRepository(
        db: RickAndMortyDatabase
    ): GetLocationFiltersRepository {
        return GetLocationFiltersRepositoryImpl(
            db = db
        )
    }


    @Provides
    @Singleton
    fun provideGetEpisodeFiltersRepository(
        db: RickAndMortyDatabase
    ): GetEpisodeFiltersRepository {
        return GetEpisodeFiltersRepositoryImpl(
            db = db
        )
    }


    @Provides
    @Singleton
    fun provideEpisodesRepository(
        db: RickAndMortyDatabase,
        episodesApi: EpisodesApi,
        episodeDetailsApi: EpisodeDetailsApi
    ): EpisodesRepository {
        return EpisodesRepositoryImpl(
            episodesApi = episodesApi,
            episodeDetailsApi = episodeDetailsApi,
            db = db
        )
    }


    @Provides
    @Singleton
    fun provideEpisodeDetailsRepository(
        db: RickAndMortyDatabase,
        episodeDetailsApi: EpisodeDetailsApi
    ): EpisodeDetailsRepository {
        return EpisodeDetailsRepositoryImpl(
            episodeDetailsApi = episodeDetailsApi,
            db = db
        )
    }


    @Provides
    @Singleton
    fun provideGetCharacterFiltersRepository(
        db: RickAndMortyDatabase
    ): GetCharacterFiltersRepository {
        return GetCharacterFiltersRepositoryImpl(db = db)
    }


    @Provides
    @Singleton
    fun provideCharactersRepository(
        db: RickAndMortyDatabase,
        characterDetailsApi: CharacterDetailsApi,
        charactersApi: CharactersApi,
    ): CharactersRepository {
        return CharactersRepositoryImpl(
            characterDetailsApi = characterDetailsApi,
            charactersApi = charactersApi,
            db = db
        )
    }


    @Provides
    @Singleton
    fun provideCharacterDetailsRepository(
        db: RickAndMortyDatabase,
        characterDetailsApi: CharacterDetailsApi
    ): CharacterDetailsRepository {
        return CharacterDetailsRepositoryImpl(
            characterDetailsApi = characterDetailsApi,
            db = db
        )
    }

    @Provides
    @Singleton
    fun provideRickAndMortyDatabase(context: Context): RickAndMortyDatabase {
        return Room.databaseBuilder(
            context,
            RickAndMortyDatabase::class.java,
            "RickAndMortyDB.bd"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCharacterDetailsApi(): CharacterDetailsApi {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharacterDetailsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCharactersApi(): CharactersApi {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharactersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEpisodeDetailsApi(): EpisodeDetailsApi {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EpisodeDetailsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEpisodesApi(): EpisodesApi {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EpisodesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationDetailsApi(): LocationDetailsApi {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LocationDetailsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationsApi(): LocationsApi {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LocationsApi::class.java)
    }
}
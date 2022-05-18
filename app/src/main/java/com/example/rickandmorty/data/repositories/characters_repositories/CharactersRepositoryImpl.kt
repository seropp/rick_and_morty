package com.example.rickandmorty.data.repositories.characters_repositories

import android.util.Log
import androidx.paging.*
import com.example.rickandmorty.data.mapper.entity_to_domain_model.CharacterEntityToDomainModel
import com.example.rickandmorty.data.models.characters.Characters
import com.example.rickandmorty.data.paging.characters_paging.CharactersRemoteMediator
import com.example.rickandmorty.data.remote.api.chatacters.CharacterDetailsApi
import com.example.rickandmorty.data.remote.api.chatacters.CharactersApi
import com.example.rickandmorty.data.storage.room.db.RickAndMortyDatabase
import com.example.rickandmorty.domain.models.character.CharacterModel
import com.example.rickandmorty.domain.repositories.characters_repositories.CharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


@ExperimentalPagingApi
class CharactersRepositoryImpl(
    private val characterDetailsApi: CharacterDetailsApi,
    private val charactersApi: CharactersApi,
    private val db: RickAndMortyDatabase
) : CharactersRepository {

    override fun getAllCharacters(
        name: String?,
        status: String?,
        gender: String?,
        type: String?,
        species: String?
    ): Flow<PagingData<CharacterModel>> {

        val pagingSourceFactory =
            {
                db.getCharacterDao().getFilteredCharacters(
                    name = name,
                    status = status,
                    gender = gender,
                    type = type,
                    species = species
                )
            }

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true,
            ),
            remoteMediator = CharactersRemoteMediator(
                charactersApi = charactersApi,
                db = db,
                name = name,
                status = status,
                gender = gender,
                type = type,
                species = species
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { it ->
                CharacterEntityToDomainModel().transform(it)
            }
        }
    }

    override suspend fun getAllCharactersByIds(ids: List<Int>): List<CharacterModel> =
        withContext(Dispatchers.IO) {
            try {
                if (ids.size > 1) {
                    val idsString: String = ids.joinToString(separator = ",")
                    val charactersFromApi: Response<List<Characters>> =
                        charactersApi.getCharactersByIds(ids = idsString)
                    if (charactersFromApi.isSuccessful) {
                        charactersFromApi.body()
                            ?.let { db.getCharacterDao().insertAllCharacters(characters = it) }
                    }
                }
                if (ids.size == 1) {
                    val characterFromApi: Response<Characters> =
                        characterDetailsApi.getCharacterById(id = ids[0])
                    if (characterFromApi.isSuccessful) {
                        characterFromApi.body()
                            ?.let { db.getCharacterDao().insertCharacter(character = it) }
                    }
                }

            } catch (e: HttpException) {
                Log.e("Log", "${e.code()}")
            } catch (e: IOException) {
                Log.e("Log", "${e.message}")
            }

            return@withContext db.getCharacterDao().getCharactersByIds(ids = ids).map {
                CharacterEntityToDomainModel().transform(it)
            }
        }
}

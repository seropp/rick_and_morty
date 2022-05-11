package com.example.rickandmorty.data.paging.characters_paging

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.mapper.dto_to_entity.CharacterDtoToEntity
import com.example.rickandmorty.data.remote.api.chatacters.CharactersApi
import com.example.rickandmorty.data.remote.dto.characterDto.CharacterDto
import com.example.rickandmorty.data.remote.dto.pagesInfo.PagedResponse
import com.example.rickandmorty.data.storage.room.entities.character.CharacterEntity
import retrofit2.Response

class CharactersPagingDataSource(
    private val charactersApi: CharactersApi
) : PagingSource<Int, CharacterEntity>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
        val pageNumber = params.key ?: 1
        return try {
            val response: Response<PagedResponse<CharacterDto>> = charactersApi.getCharacterPage(pageNumber)
            val pagedResponse: PagedResponse<CharacterDto>? = response.body()
            val data: List<CharacterDto>? = pagedResponse?.results
            val mapList = data?.map{
                CharacterDtoToEntity().transform(it)
            }
            var nextPageNumber: Int? = null

            if (pagedResponse?.pageInfo?.next != null) {
                val uri = Uri.parse(pagedResponse.pageInfo.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }
            Log.e("TAGGGGGGGGGGGGGGGGGG", "3")

            LoadResult.Page(
                data = mapList.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterEntity>): Int = 1
}
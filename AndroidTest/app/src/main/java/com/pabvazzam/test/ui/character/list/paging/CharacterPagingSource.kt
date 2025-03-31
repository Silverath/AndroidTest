package com.pabvazzam.test.ui.character.list.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pabvazzam.test.data.Character
import com.pabvazzam.test.ui.character.list.CharacterUiState
import com.pabvazzam.test.usecase.character.GetCharactersApiUseCase
import com.pabvazzam.test.usecase.character.GetCharactersFavUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CharacterPagingSource(
    private val getCharactersApiUseCase: GetCharactersApiUseCase,
    private val getCharactersFavUseCase: GetCharactersFavUseCase,
    private val _uiState: MutableStateFlow<CharacterUiState>
) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, Character> {

        return try {
            val currentPage = params.key ?: 1
            val response = getCharactersApiUseCase.invoke(currentPage)
            val favCharacters = mutableListOf<Character>()
            getCharactersFavUseCase.invoke().onSuccess { favCharacters.addAll(it) }
            val responseData = mutableListOf<Character>()
            val data = response.getOrNull() ?: emptyList()
            responseData.addAll(data)

            _uiState.update {
                CharacterUiState.Success(
                    characters = responseData.toList(),
                    favCharacters = favCharacters.toList()
                )
            }

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {

            _uiState.update {
                CharacterUiState.Error
            }
            LoadResult.Error(e)
        }

    }
}
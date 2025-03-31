package com.pabvazzam.test.ui.character.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.pabvazzam.test.data.Character
import com.pabvazzam.test.ui.character.list.paging.CharacterPagingSource
import com.pabvazzam.test.usecase.character.AddFavCharacterUseCase
import com.pabvazzam.test.usecase.character.GetCharactersApiUseCase
import com.pabvazzam.test.usecase.character.GetCharactersFavUseCase
import com.pabvazzam.test.usecase.character.RemoveFavCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersApiUseCase,
    private val addFavCharacterUseCase: AddFavCharacterUseCase,
    private val removeFavCharacterUseCase: RemoveFavCharacterUseCase,
    private val getCharactersFavUseCase: GetCharactersFavUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterUiState>(CharacterUiState.Loading)
    val uiState: StateFlow<CharacterUiState> = _uiState.asStateFlow()

    fun fetchCharacters(characterAdapter: CharacterAdapter) {
        viewModelScope.launch {
            Pager(PagingConfig(pageSize = 10)) {
                CharacterPagingSource(getCharactersUseCase, getCharactersFavUseCase, _uiState)
            }.flow.collectLatest {
                characterAdapter.submitData(it)
            }
        }
    }

    fun onFavChanged(character: Character, isFav: Boolean, onFavModified: (Boolean) -> Unit) {
        if (isFav) {
            val newFavList = removeFavCharacterUseCase.invoke(character)
            newFavList.onSuccess { newList ->
                _uiState.update {
                    (_uiState.value as CharacterUiState.Success).copy(
                        favCharacters = newList
                    )
                }
                onFavModified(newFavList.isSuccess)
            }
        } else {
            val newFavList = addFavCharacterUseCase.invoke(character)
            newFavList.onSuccess { newList ->
                _uiState.update {
                    (_uiState.value as CharacterUiState.Success).copy(
                        favCharacters = newList
                    )
                }
                onFavModified(newFavList.isSuccess)
            }
        }
    }
}
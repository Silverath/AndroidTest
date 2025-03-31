package com.pabvazzam.test.ui.character.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.pabvazzam.test.data.Character
import com.pabvazzam.test.ui.character.list.paging.CharacterPagingSource
import com.pabvazzam.test.usecase.character.AddFavCharacterUseCase
import com.pabvazzam.test.usecase.character.GetCharactersApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersApiUseCase,
    private val addFavCharacterUseCase: AddFavCharacterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterUiState>(CharacterUiState.Loading)
    val uiState: StateFlow<CharacterUiState> = _uiState.asStateFlow()

    fun fetchCharacters(characterAdapter: CharacterAdapter) {
        viewModelScope.launch {
            Pager(PagingConfig(pageSize = 10)) {
                CharacterPagingSource(getCharactersUseCase, _uiState)
            }.flow.collectLatest {
                characterAdapter.submitData(it)
            }
        }
    }

    fun onFavChanged(character: Character, onAddedFavCharacter: (Boolean) -> Unit) {
        onAddedFavCharacter(addFavCharacterUseCase.invoke(character).isSuccess)
    }
}
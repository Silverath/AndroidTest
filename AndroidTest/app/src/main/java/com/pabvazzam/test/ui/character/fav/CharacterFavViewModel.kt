package com.pabvazzam.test.ui.character.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pabvazzam.test.usecase.character.GetCharactersFavUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterFavViewModel @Inject constructor(
    private val getCharactersFavUseCase: GetCharactersFavUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterFavUiState())
    val uiState: StateFlow<CharacterFavUiState> = _uiState.asStateFlow()

    fun fetchCharacters() {
        viewModelScope.launch {
            val result = getCharactersFavUseCase.invoke()
            result.onSuccess { data ->
                _uiState.update {
                    _uiState.value.copy(
                        characters = data
                    )
                }
            }
        }
    }
}
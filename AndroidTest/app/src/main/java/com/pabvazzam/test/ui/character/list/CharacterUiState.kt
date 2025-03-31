package com.pabvazzam.test.ui.character.list

import com.pabvazzam.test.data.Character

sealed interface CharacterUiState {
    data object Loading : CharacterUiState

    data object Error : CharacterUiState

    data class Success(
        val firstLoad: Boolean = true,
        val characters: List<Character>,
        val favCharacters: List<Character>
    ) : CharacterUiState
}
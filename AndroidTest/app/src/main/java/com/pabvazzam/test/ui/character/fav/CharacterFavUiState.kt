package com.pabvazzam.test.ui.character.fav

import com.pabvazzam.test.data.Character

data class CharacterFavUiState(
    val characters: List<Character> = emptyList()
)
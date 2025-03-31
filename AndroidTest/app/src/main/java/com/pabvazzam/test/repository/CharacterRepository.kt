package com.pabvazzam.test.repository

import com.pabvazzam.test.data.Character

interface CharacterRepository {

    suspend fun getCharactersFromApi(page: Int): List<Character>?
    fun addFavCharacter(character: Character): List<Character>
    fun getFavCharacters(): List<Character>
    fun removeFavCharacter(character: Character): List<Character>
}